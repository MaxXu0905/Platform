package com.ailk.sets.grade.utils;

public class TemplateUtils {

	public static class Result {
		private String content;
		private boolean hide;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public boolean isHide() {
			return hide;
		}

		public void setHide(boolean hide) {
			this.hide = hide;
		}
	}

	public static Result getTemplate(String content) {
		Result result = new Result();
		String[] fields = content.split("\n");
		StringBuilder builder = new StringBuilder();

		boolean hide = true;
		boolean skip = false;
		for (String field : fields) {
			String trimStr = field.trim();

			if (trimStr.equals("// BEGIN")) {
				skip = true;
				hide = false;
			} else if (trimStr.equals("// END")) {
				skip = false;
			} else if (!skip) {
				builder.append(field);
				builder.append("\n");
			}
		}

		result.setContent(builder.toString());
		result.setHide(hide);
		return result;
	}

}
