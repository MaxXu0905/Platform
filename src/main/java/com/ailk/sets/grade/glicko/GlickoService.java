package com.ailk.sets.grade.glicko;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateRatingQuestionDao;
import com.ailk.sets.grade.dao.intf.IQbQuestionDetailDao;
import com.ailk.sets.grade.dao.intf.IQuestionRatingDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.GetQInfoResponse;
import com.ailk.sets.grade.jdbc.CandidateRatingQuestion;
import com.ailk.sets.grade.jdbc.CandidateRatingQuestionPK;
import com.ailk.sets.grade.jdbc.QbQuestionDetail;
import com.ailk.sets.grade.jdbc.QuestionRating;
import com.ailk.sets.grade.jdbc.QuestionRatingPK;
import com.ailk.sets.grade.security.DESCoder;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.google.gson.Gson;

@Transactional(rollbackFor = Exception.class)
@Service
public class GlickoService implements IGlickoService {

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IQuestionRatingDao questionRatingDao;

	@Autowired
	private ICandidateRatingQuestionDao candidateRatingQuestionDao;

	@Autowired
	private IQbQuestionDetailDao qbQuestionDetailDao;

	@Value("${grade.minGlickoNum}")
	private int minGlickoNum;

	private Gson gson;
	private Glicko glicko;

	@PostConstruct
	public void init() {
		gson = new Gson();
		glicko = new Glicko();
	}

	@Override
	public void ratingQuestion() {
		List<QbQuestion> qbQuestions = qbQuestionDao.getList();
		Settings settings = glicko.getSettings();

		for (QbQuestion qbQuestion : qbQuestions) {
			if (qbQuestion.getAnswerNum() < minGlickoNum) {
				qbQuestion.setAnswerNum(100);
				qbQuestion.setCorrectNum(100 - qbQuestion.getDegree() * 7);
			}

			glicko.removePlayers();

			PlayerKey playerKey = new PlayerKey();
			playerKey.setId(qbQuestion.getQuestionId());
			playerKey.setType(Glicko.PLAYER_TYPE_QUESTION);

			Player player = new Player(settings);
			player.setPlayerKey(playerKey);
			glicko.addPlayer(player);

			Player opponent = player;

			List<Match> matches = new ArrayList<Match>();
			for (int i = 0; i < qbQuestion.getAnswerNum(); i++) {
				playerKey = new PlayerKey();
				playerKey.setId(i);
				playerKey.setType(qbQuestion.getQbId());

				player = new Player(settings);
				player.setPlayerKey(playerKey);
				glicko.addPlayer(player);

				Match match = new Match();
				match.setPlayer1(player);
				match.setPlayer2(opponent);
				match.setOutcome(i < qbQuestion.getCorrectNum() ? 1 : 0);
				matches.add(match);
			}

			glicko.updateRatings(matches);

			QuestionRatingPK questionRatingPK = new QuestionRatingPK();
			questionRatingPK.setQbId(qbQuestion.getQbId());
			questionRatingPK.setQuestionId(qbQuestion.getQuestionId());

			QuestionRating questionRating = new QuestionRating();
			questionRating.setQuestionRatingPK(questionRatingPK);
			questionRating.setTau(opponent.getTau());
			questionRating.setRating(opponent.getRating());
			questionRating.setRd(opponent.getRd());
			questionRating.setVol(opponent.getVol());
			questionRatingDao.saveOrUpdate(questionRating);
		}
	}

	@Override
	public void loadQuestionPlayers() {
		glicko.removePlayers();

		List<QuestionRating> questionRatings = questionRatingDao.getList();
		for (QuestionRating questionRating : questionRatings) {
			PlayerKey playerKey = new PlayerKey();
			playerKey.setId(questionRating.getQuestionRatingPK()
					.getQuestionId());
			playerKey.setType(Glicko.PLAYER_TYPE_QUESTION);

			Player player = new Player();
			player.setPlayerKey(playerKey);
			player.setTau(questionRating.getTau());
			player.setRating(questionRating.getRating());
			player.setRd(questionRating.getRd());
			player.setVol(questionRating.getVol());
			glicko.addPlayer(player);
		}
	}

	@Override
	public RatingSession buildRatingSession(int candidateId, int qbId) {
		RatingSession ratingSession = new RatingSession();
		Set<Long> ratedSet = candidateRatingQuestionDao.getSet(candidateId,
				qbId);

		PlayerKey playerKey = new PlayerKey();
		playerKey.setId(candidateId);
		playerKey.setType(qbId);
		Player player = glicko.getPlayer(playerKey);
		if (player == null) {
			player = new Player(glicko.getSettings());
			player.setPlayerKey(playerKey);
			ratingSession.setPlayer(player);
		}

		TreeMap<Double, Player> playerMap = new TreeMap<Double, Player>();
		List<Player> qbPlayers = glicko.getQbPlayers(qbId);
		for (Player qbPlayer : qbPlayers) {
			playerKey = qbPlayer.getPlayerKey();
			if (ratedSet.contains(playerKey.getId()))
				continue;

			playerMap.put(qbPlayer.getRating(), qbPlayer);
		}

		ratingSession.setPlayerMap(playerMap);
		return ratingSession;
	}

	@Override
	public GetQInfoResponse getQInfo(RatingSession ratingSession)
			throws Exception {
		GetQInfoResponse response = new GetQInfoResponse();
		TreeMap<Double, Player> playerMap = ratingSession.getPlayerMap();
		Player player = ratingSession.getPlayer();

		if (playerMap.isEmpty()) {
			// 没有题了
		}

		Map<Double, Player> map = playerMap.tailMap(player.getRating());
		if (map == null)
			map = playerMap.descendingMap().tailMap(player.getRating());

		Player nextPlayer = map.values().iterator().next();
		long qid = nextPlayer.getPlayerKey().getId();

		// 加载
		QbQuestionDetail qbQuestionDetail = qbQuestionDetailDao.get(qid);
		if (qbQuestionDetail == null) {
			response.setErrorCode(BaseResponse.EINVAL);
			response.setErrorDesc("找不到试题详情，qid=" + qid);
			return response;
		}

		String content = qbQuestionDetail.getContent();
		if (qbQuestionDetail.isEncrypted()) {
			content = new String(DESCoder.decrypt(
					content.getBytes(GradeConst.ENCODING), GradeConst.DES_KEY),
					GradeConst.ENCODING);
		}
		QuestionContent questionContent = gson.fromJson(content,
				QuestionContent.class);
		QuestionConf questionConf = questionContent.getQuestionConf();

		response.setQbName(questionConf.getQbName());
		response.setMode(questionConf.getMode());
		response.setTitle(questionConf.getTitle());
		response.setCategory(questionConf.getCategory());
		response.setType(questionConf.getTypeInt());
		response.setOptions(questionConf.getOptions());
		response.setEditType(questionConf.getTypeInt());

		return response;
	}

	@Override
	public BaseResponse commit(RatingSession ratingSession, String answer) {
		BaseResponse response = new BaseResponse();
		int outcome;

		if (answer == null)
			outcome = 0;
		else
			outcome = answer.equals(ratingSession.getAnswer()) ? 1 : 0;

		Player player = ratingSession.getPlayer();
		CandidateRatingQuestion candidateRatingQuestion = new CandidateRatingQuestion();
		CandidateRatingQuestionPK candidateRatingQuestionPK = new CandidateRatingQuestionPK();
		candidateRatingQuestionPK.setCandidateId((int) player.getPlayerKey()
				.getId());
		candidateRatingQuestionPK.setQbId(player.getPlayerKey().getType());

		candidateRatingQuestion
				.setCandidateRatingQuestionPK(candidateRatingQuestionPK);
		candidateRatingQuestion.setOutcome(outcome);
		candidateRatingQuestion.setOrderId(ratingSession.getOrderId());
		candidateRatingQuestionDao.saveOrUpdate(candidateRatingQuestion);

		return response;
	}

}
