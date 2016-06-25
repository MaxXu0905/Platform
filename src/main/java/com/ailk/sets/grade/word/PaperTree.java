package com.ailk.sets.grade.word;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;

class PaperTree {

	public static final int PART = 0;
	public static final int SKILL = 1;
	public static final int QUESION = 2;
	public static final int SUB_QUESTION = 3;

	private List<IBodyElement> descriptions;
	private List<Part> parts;

	public void addDescription(IBodyElement bodyElement) {
		if (descriptions == null)
			descriptions = new ArrayList<IBodyElement>();

		descriptions.add(bodyElement);
	}

	public Part newPart() {
		if (parts == null)
			parts = new ArrayList<PaperTree.Part>();

		Part part = new Part();
		parts.add(part);
		return part;
	}

	public List<IBodyElement> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<IBodyElement> descriptions) {
		this.descriptions = descriptions;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	@Override
	public String toString() {
		return "PaperTree [descriptions=" + descriptions + ", parts=" + parts
				+ "]";
	}

	static class Part {
		private String partName;
		private IBodyElement part;
		private List<IBodyElement> descriptions;
		private List<Skill> skills;

		public void addDescription(IBodyElement bodyElement) {
			if (descriptions == null)
				descriptions = new ArrayList<IBodyElement>();

			descriptions.add(bodyElement);
		}

		public Skill newSkill() {
			if (skills == null)
				skills = new ArrayList<PaperTree.Skill>();

			Skill skill = new Skill();
			skills.add(skill);
			return skill;
		}

		public String getPartName() {
			return partName;
		}

		public void setPartName(String partName) {
			this.partName = partName;
		}

		public IBodyElement getPart() {
			return part;
		}

		public void setPart(IBodyElement part) {
			this.part = part;
		}

		public List<IBodyElement> getDescriptions() {
			return descriptions;
		}

		public void setDescriptions(List<IBodyElement> descriptions) {
			this.descriptions = descriptions;
		}

		public List<Skill> getSkills() {
			return skills;
		}

		public void setSkills(List<Skill> skills) {
			this.skills = skills;
		}

		@Override
		public String toString() {
			return "Part [partName=" + partName + ", descriptions="
					+ descriptions + ", skills=" + skills + "]";
		}
	}

	static class Skill {
		private String skillName;
		private IBodyElement skill;
		private List<IBodyElement> descriptions;
		private List<Question> questions;

		public void addDescription(IBodyElement bodyElement) {
			if (descriptions == null)
				descriptions = new ArrayList<IBodyElement>();

			descriptions.add(bodyElement);
		}

		public Question newQuestion() {
			if (questions == null)
				questions = new ArrayList<PaperTree.Question>();

			Question question = new Question();
			questions.add(question);
			return question;
		}

		public IBodyElement getSkill() {
			return skill;
		}

		public void setSkill(IBodyElement skill) {
			this.skill = skill;
		}

		public String getSkillName() {
			return skillName;
		}

		public void setSkillName(String skillName) {
			this.skillName = skillName;
		}

		public List<IBodyElement> getDescriptions() {
			return descriptions;
		}

		public void setDescriptions(List<IBodyElement> descriptions) {
			this.descriptions = descriptions;
		}

		public List<Question> getQuestions() {
			return questions;
		}

		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}

		@Override
		public String toString() {
			return "Skill [skillName=" + skillName + ", descriptions="
					+ descriptions + ", questions=" + questions + "]";
		}
	}

	static class Question {
		private List<IBodyElement> titles;
		private List<Choice> choices;

		public void addTitle(IBodyElement bodyElement) {
			if (titles == null)
				titles = new ArrayList<IBodyElement>();

			titles.add(bodyElement);
		}

		public Choice newChoice() {
			if (choices == null)
				choices = new ArrayList<Choice>();

			Choice choice = new Choice();
			choices.add(choice);
			return choice;
		}

		public List<IBodyElement> getTitles() {
			return titles;
		}

		public void setTitles(List<IBodyElement> titles) {
			this.titles = titles;
		}

		public List<Choice> getChoices() {
			return choices;
		}

		public void setChoices(List<Choice> choices) {
			this.choices = choices;
		}

		@Override
		public String toString() {
			return "Question [titles=" + titles + ", choices=" + choices + "]";
		}
	}

	static class Choice {
		private List<IBodyElement> elements;
		private List<Choice> subChoices;

		public void addElement(IBodyElement element) {
			if (elements == null)
				elements = new ArrayList<IBodyElement>();

			elements.add(element);
		}

		public Choice newSubChoice() {
			if (subChoices == null)
				subChoices = new ArrayList<Choice>();

			Choice subChoice = new Choice();
			subChoices.add(subChoice);
			return subChoice;
		}

		public List<IBodyElement> getElements() {
			return elements;
		}

		public void setElements(List<IBodyElement> elements) {
			this.elements = elements;
		}

		public List<Choice> getSubChoices() {
			return subChoices;
		}

		public void setSubChoices(List<Choice> subChoices) {
			this.subChoices = subChoices;
		}

		@Override
		public String toString() {
			return "Choice [elements=" + elements + ", subChoices="
					+ subChoices + "]";
		}
	}

}
