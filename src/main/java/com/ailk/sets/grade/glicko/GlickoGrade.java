package com.ailk.sets.grade.glicko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.dao.intf.IQuestionRatingDao;
import com.ailk.sets.grade.jdbc.QuestionRating;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;

@Transactional(rollbackFor = Exception.class)
@Service
public class GlickoGrade implements IGlickoGrade {

	@Autowired
	private IQuestionRatingDao questionRatingDao;

	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;

	public static class TestResult {
		private long questionId;
		private int outcome;

		public long getQuestionId() {
			return questionId;
		}

		public void setQuestionId(long questionId) {
			this.questionId = questionId;
		}

		public int getOutcome() {
			return outcome;
		}

		public void setOutcome(int outcome) {
			this.outcome = outcome;
		}
	}

	private Map<Long, List<TestResult>> testMap;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/beans.xml" });
		context.start();

		GlickoGrade instance = context.getBean(GlickoGrade.class);
		instance.execute();
	}

	@Override
	public void execute() {
		testMap = new HashMap<Long, List<TestResult>>();
		List<CandidateTestQuestion> candidateTestQuestions = candidateTestQuestionDao
				.getList();
		
		Set<Long> qbSet = new HashSet<Long>();
		qbSet.add(1070400000L);
		qbSet.add(1070400100L);
		qbSet.add(1070400700L);
		qbSet.add(1070400800L);
		qbSet.add(1070400900L);
		qbSet.add(1070401300L);
		qbSet.add(1070401700L);

		for (CandidateTestQuestion candidateTestQuestion : candidateTestQuestions) {
			if (candidateTestQuestion.getPartSeq() != 1)
				continue;
			
			if (!qbSet.contains(candidateTestQuestion.getId().getQuestionId() / 100000))
				continue;
			
			List<TestResult> testResults = testMap.get(candidateTestQuestion
					.getId().getTestId());
			if (testResults == null) {
				testResults = new ArrayList<TestResult>();
				testMap.put(candidateTestQuestion.getId()
						.getTestId(), testResults);
			}

			TestResult testResult = new TestResult();
			testResult.setQuestionId(candidateTestQuestion
					.getId().getQuestionId());
			testResult.setOutcome(candidateTestQuestion.getCorrectFlag());
			testResults.add(testResult);
		}

		Glicko glicko = new Glicko();
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

		Settings settings = glicko.getSettings();

		List<Match> matches = new ArrayList<Match>();
		for (Entry<Long, List<TestResult>> entry : testMap.entrySet()) {
			long testId = entry.getKey();
			List<TestResult> testResults = entry.getValue();

			PlayerKey playerKey = new PlayerKey();
			playerKey.setId(testId);
			playerKey.setType(0);

			Player player = new Player(settings);
			player.setPlayerKey(playerKey);
			glicko.addPlayer(player);

			for (TestResult testResult : testResults) {
				playerKey = new PlayerKey();
				playerKey.setId(testResult.getQuestionId());
				playerKey.setType(Glicko.PLAYER_TYPE_QUESTION);
				Player opponent = glicko.getPlayer(playerKey);
				if (opponent == null)
					continue;

				Match match = new Match();
				match.setPlayer1(player);
				match.setPlayer2(opponent);
				match.setOutcome(testResult.getOutcome());
				matches.add(match);
			}
		}

		glicko.updateRatings(matches);

		Map<PlayerKey, Player> playerMap = glicko.getPlayerMap();
		for (Player player : playerMap.values()) {
			if (player.getPlayerKey().getType() == Glicko.PLAYER_TYPE_QUESTION)
				continue;

			System.out
					.println(player.getPlayerKey().getId() + "\t"
							+ player.getRealRating(settings) + "\t"
							+ player.getRealRd());
		}
	}
}
