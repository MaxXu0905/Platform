package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IPaperQuestionDao;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
@Repository
public class PaperQuestionDaoImpl extends BaseDaoImpl<PaperQuestion> implements IPaperQuestionDao {
	public List<PaperQuestion> getNormalPaperQuestions(int paperId){
		Session session = getSession();  
		Query query = session.createQuery("from PaperQuestion where partSeq < 20 and id.paperId = " + paperId);
		return (List<PaperQuestion>)query.list();
	}
	
	public List<PaperQuestion> getPaperQuestionsByPaperIdAndSeq(int paperId, int partSeq) {
		Session session = getSession();
		Query query = session.createQuery("from PaperQuestion where partSeq = " + partSeq + " and id.paperId = "
				+ paperId +" order by questionSeq");
		return (List<PaperQuestion>) query.list();
	}
}
