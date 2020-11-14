package com.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component //marcar como componente spring
public class PreTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log=LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);
	
	@Override
	public boolean shouldFilter() {
		//Para validar
		//Si vamos a ejecutar o no el filtro
		//Validaciones: Existe un parametro en la ruta, si el usuario esta autenticado
	
		return true; //true=ejecuta el filtro
	}

	@Override
	public Object run() throws ZuulException {		
		//Se resuelve la logica del filtro		
		RequestContext ctx=RequestContext.getCurrentContext();
		HttpServletRequest request=ctx.getRequest();
		log.info(String.format("%s request enrutado a %s",request.getMethod(), request.getRequestURL().toString()));
		Long tiempoInicio=System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		return null;
	}

	@Override
	public String filterType() {
		return "pre";//palabra clave
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
