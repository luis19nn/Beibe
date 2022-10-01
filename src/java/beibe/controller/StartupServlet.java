/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.ConfigBean;
import beibe.exceptions.FacadeException;
import beibe.facade.GeralFacade;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

/**
 *
 * @author luis
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"}, loadOnStartup = 1)
public class StartupServlet extends HttpServlet {
//    Dentro do seu método init(), deve-se armazenar no escopo da aplicação (ServletContext) uma instância preenchida de um ConfigBean, com o nome "configuracao".
    @Override
    public void init(ServletConfig config) {
        ConfigBean conf = new ConfigBean();
        conf.setEmailAdministrador("GrupoWebII@gmail.com");
        
        try {
            conf.setListaCargos(GeralFacade.getListaCargos());
            conf.setListaCidades(GeralFacade.getListaCidades());
            conf.setListaEstados(GeralFacade.getListaEstados());
        } catch (FacadeException ex) {
            Logger.getLogger(StartupServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        ServletContext ctx = config.getServletContext();
        ctx.setAttribute("configuracao", conf);
    }   
   
}
