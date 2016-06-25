package com.ailk.sets.grade.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICompanyTemplateDao;
import com.ailk.sets.grade.dao.intf.IConfigRegionDao;
import com.ailk.sets.grade.dao.intf.IConfigTemplateDao;
import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.intf.report.InterviewInfo;
import com.ailk.sets.grade.intf.report.InterviewInfo.Group;
import com.ailk.sets.grade.intf.report.InterviewInfo.Item;
import com.ailk.sets.grade.jdbc.CompanyTemplate;
import com.ailk.sets.grade.jdbc.CompanyTemplatePK;
import com.ailk.sets.grade.jdbc.ConfigTemplate;
import com.ailk.sets.grade.service.intf.IInterviewTemplateService;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;

/**
 * 面试模板管理
 * 
 * @author xugq
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class InterviewTemplateServiceImpl implements IInterviewTemplateService {

	private static final Logger logger = Logger
			.getLogger(InterviewTemplateServiceImpl.class);
	private static Mapping mapping;

	@Autowired
	private IConfigTemplateDao configTemplateDao;

	@Autowired
	private ICompanyTemplateDao companyTemplateDao;

	@Autowired
	private ICandidateDao candidateDao;

	@Autowired
	private IConfigRegionDao configRegionDao;

	static {
		try {
			mapping = new Mapping();
			mapping.loadMapping(InterviewTemplateServiceImpl.class
					.getClassLoader().getResource("interview_mapping.xml"));
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
			System.exit(1);
		}
	}

	@Override
	public InterviewInfo load(int employerId, int testType, String templateId)
			throws Exception {
		StringReader reader = null;
		CompanyTemplate companyTemplate = companyTemplateDao.get(employerId,
				testType, templateId);
		if (companyTemplate == null) {
			ConfigTemplate configTemplate = configTemplateDao.get(testType,
					templateId);
			if (configTemplate == null)
				return null;

			reader = new StringReader(configTemplate.getContent());
		} else {
			reader = new StringReader(companyTemplate.getContent());
		}

		Unmarshaller unmarshaller = new Unmarshaller(InterviewInfo.class);
		unmarshaller.setMapping(mapping);
		unmarshaller.setWhitespacePreserve(true);
		InterviewInfo interviewInfo = (InterviewInfo) unmarshaller
				.unmarshal(reader);

		List<Group> groups = interviewInfo.getGroups();
		if (groups != null) {
			for (Group group : groups) {
				List<Item> items = group.getItems();
				if (items == null)
					continue;

				for (Item item : items) {
					if (item.getValueSql() != null) {
						item.setMappings(candidateDao.getMapping(item
								.getValueSql()));
					} else if (item.getType() == "address") {
						item.setConfigRegionInfos(configRegionDao
								.getConfigRegionInfos());
					}
				}

			}
		}

		return interviewInfo;
	}

	@Override
	public void save(int employerId, int testType, String templateId,
			InterviewInfo interviewInfo) throws Exception {
		StringWriter out = null;
		try {
			out = new StringWriter();
			OutputFormat format = new OutputFormat(Method.XML,
					GradeConst.ENCODING, true);
			String[] cndata = { "description", "valueSql", "verifyExp" };
			format.setCDataElements(cndata);

			XMLSerializer serializer = new XMLSerializer(out, format);
			Marshaller marshaller = new Marshaller(
					serializer.asDocumentHandler());
			marshaller.setMapping(mapping);
			marshaller.marshal(this);

			CompanyTemplate companyTemplate = new CompanyTemplate();
			CompanyTemplatePK companyTemplatePK = new CompanyTemplatePK();
			companyTemplatePK.setEmployerId(employerId);
			companyTemplatePK.setTestType(testType);
			companyTemplatePK.setTemplateId(templateId);
			companyTemplate.setCompanyTemplatePK(companyTemplatePK);
			companyTemplate.setContent(out.toString());

			companyTemplateDao.saveOrUpdate(companyTemplate);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

}
