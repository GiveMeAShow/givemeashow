package giveme.controllers;

import giveme.common.beans.ISOLang;
import giveme.common.dao.ISOLangDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LangController {
	
	@Autowired
	ISOLangDao langDao;
	
	@RequestMapping(value = "/admin/lang/new", method = RequestMethod.GET)
	public ModelAndView showNewPage()
	{
		ModelAndView mdv = new ModelAndView("/admin/lang/creteNew");
		mdv.addObject("lang", new ISOLang());
		return mdv;
	}

	@RequestMapping(value = "/admin/lang/insert", method = RequestMethod.POST)
	public ModelAndView insertLang()
	{
		ModelAndView mdv = new ModelAndView("/admin/lang/validInsertion");
		return mdv;
	}
}
