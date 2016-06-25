package com.ailk.sets.platform.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.domain.DegreeSkillSearchCondition;
import com.ailk.sets.platform.domain.DegreeToSkillLabels;
import com.ailk.sets.platform.domain.PositionLevelDegreeTime;
import com.ailk.sets.platform.domain.PositionLevelDegreeTimeId;
import com.ailk.sets.platform.domain.QbQuestionView;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.skilllabel.PositionSkillScopeView;
import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceSep;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.model.qb.QbSkill;

/**
 * 试卷生成工具类
 * 
 * @author panyl
 * 
 */
public class PaperCreateUtils {

	/**
	 * 随机返回一个试题
	 * 
	 * @param questionIds
	 * @return
	 */
	public static <T> T randomQuestions(List<T> objects) {
		double random = Math.random();
		int index = (int) (random * (objects.size()));
		return objects.get(index);
	}

	/**
	 * 返回in语句
	 * 
	 * @param skillIds
	 * @return
	 */
	public static String getSkillsInStr(List<String> skillIds) {
		StringBuffer sb = new StringBuffer();
		if (skillIds.size() > 0) {
			sb.append('(');
			for (String skillId : skillIds) {
				sb.append(skillId).append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(')');
		}
		return sb.toString();
	}

	public static String getIntegerInStr(Collection<Integer> degrees) {

		StringBuffer sb = new StringBuffer();
		if (degrees.size() > 0) {
			sb.append('(');
			for (Integer degree : degrees) {
				sb.append(degree).append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(')');
		}
		return sb.toString();

	}

	public static String getLongInStr(Collection<Long> degrees) {

		StringBuffer sb = new StringBuffer();
		if (degrees.size() > 0) {
			sb.append('(');
			for (Long degree : degrees) {
				sb.append(degree).append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(')');
		}
		return sb.toString();

	}

	/**
	 * 返回in语句
	 * 
	 * @param skillIds
	 * @return
	 */
	public static String getSkillsInStrNoBracket(List<String> skillIds) {
		if (skillIds == null || skillIds.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (String skillId : skillIds) {
			sb.append(skillId).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 根据类型获取题型
	 * 
	 * @param paperType
	 * @return
	 */
	public static String getQuestionTypesInStr(PaperPartSeqEnum paperType) {
		if (paperType == PaperPartSeqEnum.OBJECT) {
			return "('" + Constants.QUESTION_TYPE_NAME_S_CHOICE + "','"
					+ Constants.QUESTION_TYPE_NAME_M_CHOICE + "')";
		} else if (paperType == PaperPartSeqEnum.EXTRA) {
			return "('" + Constants.QUESTION_TYPE_NAME_TEXT + "')";
		} else if (paperType == PaperPartSeqEnum.INTEVEIW) {
			return "('" + Constants.QUESTION_TYPE_NAME_INTERVIEW + "')";
		} else {
			return "('" + Constants.QUESTION_TYPE_NAME_PROGRAM + "')";
		}
	}

	/**
	 * 根据类型获取题型
	 * 
	 * @param paperType
	 * @return
	 */
	public static String[] getQuestionTypesInArray(PaperPartSeqEnum paperType) {
		if (paperType == PaperPartSeqEnum.OBJECT) {
			return new String[] { Constants.QUESTION_TYPE_NAME_S_CHOICE,
					Constants.QUESTION_TYPE_NAME_M_CHOICE };
		} else if (paperType == PaperPartSeqEnum.EXTRA) {
			return new String[] { Constants.QUESTION_TYPE_NAME_TEXT };
		} else if (paperType == PaperPartSeqEnum.INTEVEIW) {
			return new String[] { Constants.QUESTION_TYPE_NAME_INTERVIEW };
		} else {
			return new String[] { Constants.QUESTION_TYPE_NAME_PROGRAM };
		}
	}

	/**
	 * 取子集
	 * 
	 * @param list
	 * @param n
	 * @return
	 */
	public static List<List<String>> getSubSet(List<String> list, int n) {
		if (list.size() < n || n <= 0) {
			return null;
		}
		List<List<String>> result = new ArrayList<List<String>>();
		if (list.size() == n) {
			result.add(new ArrayList<String>(list));
		} else {
			// contains the first element
			// ArrayList<String> list1 = (ArrayList<String>)((ArrayList<String>)
			// list).clone();
			List<String> list1 = new ArrayList<String>();
			list1.addAll(list);
			list1.remove(0);
			List<List<String>> res1 = getSubSet(list1, n - 1);
			if (res1 != null) {
				for (List<String> obj : res1) {
					obj.add(0, list.get(0));
					result.add(obj);
				}
			} else {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(0, list.get(0));
				result.add(temp);
			}
			// doesn't contain the first element
			List<List<String>> res2 = getSubSet(list1, n);
			if (res2 != null) {
				for (List<String> obj : res2) {
					result.add(obj);
				}
			}
		}
		return result;
	}

	public static String getDegreeTimeKey(PositionLevelDegreeTime degreeTime) {
		PositionLevelDegreeTimeId id = degreeTime.getId();
		return id.getQuestionType() + "_" + id.getPositionLanguage() + "_"
				+ id.getLevel() + "_" + id.getQuestionDegree();
	}

	public static List<PaperQuestionToSkills> changeQbQuestionsToPaperQuestion(
			List<QbQuestionView> list) {
		List<PaperQuestionToSkills> qs = new ArrayList<PaperQuestionToSkills>();
		for (QbQuestionView qb : list) {
			PaperQuestionToSkills q = new PaperQuestionToSkills();
			q.setQuestionId(qb.getId().getQuestionId());
			qs.add(q);
		}
		return qs;
	}

	public static boolean isNewSkill(
			Map<QbSkillDegree, DegreeToSkillLabels> degreeSets,
			PositionSkillScopeView skill) {
		for (DegreeToSkillLabels degreeToSkills : degreeSets.values()) {
			if (degreeToSkills.getSkillLabels().contains(skill))
				return false;
		}
		return true;
	}

	public static boolean isNeedGetFromDb(List<String> skillIds,
			Map<String, List<PaperQuestionToSkills>> degreeSkillToQuestions,
			DegreeSkillSearchCondition condition) {
		int degree = condition.getDegree();
		PaperPartSeqEnum paperType = condition.getPaperType();
		for (String key : degreeSkillToQuestions.keySet()) {
			/*
			 * if(degreeSkillToQuestions.get(key).size() > 0){ return
			 * true;//当前key有数据,需要访问数据库 }
			 */
			String[] arr = key.split("_");
			int keyDegree = Integer.valueOf(arr[0]);
			int keyPaperType = Integer.valueOf(arr[1]);
			String keySkills = arr[2];
			keySkills = keySkills.replaceFirst("\\(", "");
			keySkills = keySkills.replaceFirst("\\)", "");
			List<String> oldSkills = Arrays.asList(keySkills.split(","));
			if (keyDegree == degree && keyPaperType == paperType.getValue()
					&& skillIds.containsAll(oldSkills)
					&& degreeSkillToQuestions.get(key).size() > 0) {
				return false; // /
			}
		}
		return true;
	}

	public static List<Long> getPaperQuestionIdsFromQuesitonToSkills(
			List<PaperQuestionToSkills> paperQuestionToSkills) {
		List<Long> list = new ArrayList<Long>();
		for (PaperQuestionToSkills question : paperQuestionToSkills) {
			list.add(question.getQuestionId());
		}
		return list;
	}

	public static void sortPaperInstancePartByPartSeq(
			List<CandidateTestPart> parts) {
		Collections.sort(parts, new Comparator<CandidateTestPart>() {
			@Override
			public int compare(CandidateTestPart o1, CandidateTestPart o2) {
				Integer partSeq1 = o1.getId().getPartSeq();
				Integer partSeq2 = o2.getId().getPartSeq();
				if (partSeq1 > 20 && partSeq2 > 20) {
					return partSeq1.compareTo(partSeq2);
				}
				if (partSeq1 < 20 && partSeq2 < 20) {
					return partSeq1.compareTo(partSeq2);
				}
				return partSeq2.compareTo(partSeq1);
			}
		});
	}

	public static int getBetweenTimeForSencond(Date date1, int addSeconds,
			Date date2) {
		Calendar end = Calendar.getInstance();
		end.setTime(date1);
		end.set(Calendar.SECOND, end.get(Calendar.SECOND) + addSeconds);
		Calendar now = Calendar.getInstance();
		now.setTime(date2);
		long time1 = end.getTimeInMillis() - now.getTimeInMillis();
		return (int) (time1 / 1000);
	}

	public static String getPositionDescFromDegreeSkills(
			List<DegreeToSkillLabels> degreeToSkills) {
		StringBuffer sb = new StringBuffer();
		for (DegreeToSkillLabels degreeToSkill : degreeToSkills) {
			QbSkillDegree degree = degreeToSkill.getLabelDegree();
			List<PositionSkillScopeView> skills = degreeToSkill
					.getSkillLabels();
			if (skills.size() > 0) {
				for (int i = 0; i < skills.size(); i++) {
					if (i == 0) {
						sb.append(degree.getDegreeName());
					}
					sb.append(skills.get(i).getSkillName() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);// 删除最后一个,
				sb.append(";").append("\n");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);// 删除最后一个\n
		}
		return sb.toString();

	}

	public static String getRegexBySep(List<QbSkillSentenceSep> sentenceSeps) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (QbSkillSentenceSep sep : sentenceSeps) {
			if (sep.getSeparator().equals(".")) {
				sb.append("\\\\.").append("|");
			} else {
				sb.append(sep.getSeparator()).append("|");
			}
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("|[\u4e00-\u9fa5])");
		return sb.toString();
	}

	public static List<QbSkill> changePositionSeriesSkillViewToSkills(
			List<PositionSkillScopeView> views) {
		if (views == null)
			return null;
		List<QbSkill> list = new ArrayList<QbSkill>();
		for (PositionSkillScopeView view : views) {
			QbSkill skill = new QbSkill();
			skill.setSkillId(view.getId().getSkillId());
			skill.setSkillName(view.getSkillName());
			list.add(skill);
		}
		return list;
	}

	/*
	 * public static PaperInstancePart
	 * getNextPaperInstancePart(List<PaperInstancePart> paperParts, int
	 * currentPartSeq) { if (currentPartSeq ==
	 * PaperPartSeqEnum.TEST_OBJECT.getValue()) {
	 * 
	 * }
	 * 
	 * }
	 */
	private boolean containsPart(List<CandidateTestPart> paperParts, int partSeq) {
		for (CandidateTestPart part : paperParts) {
			if (part.getId().getPartSeq() == partSeq) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			list.add(i + 1 + "");
		}
		for (int i = 1; i <= 3; i++) {
			List<List<String>> sub = getSubSet(list, i);
			for (List<String> obj : sub) {
				for (String num : obj) {
					System.out.print(num + "   ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
