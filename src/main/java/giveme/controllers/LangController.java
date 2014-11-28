package giveme.controllers;

import giveme.common.beans.ISOLang;
import giveme.common.dao.ISOLangDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LangController
{
	private static final Logger	LOGGER	= Logger.getLogger(LangController.class.getName());

	@Autowired
	ISOLangDao					langDao;

	@RequestMapping(value = "/admin/lang/new", method = RequestMethod.GET)
	public ModelAndView showNewPage()
	{
		ModelAndView mdv = new ModelAndView("/admin/lang/createNew.jsp");
		mdv.addObject("lang", new ISOLang());
		return mdv;
	}

	@RequestMapping(value = "/admin/lang/insert", method = RequestMethod.POST)
	public ModelAndView insertLang(@ModelAttribute ISOLang lang)
	{
		LOGGER.info("saving lang " + lang.getLanguage());
		ModelAndView mdv = new ModelAndView("/admin/lang/validInsertion.jsp");
		langDao.save(lang);
		return mdv;
	}
}
