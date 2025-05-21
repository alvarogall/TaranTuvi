/**
 * @author Álvaro Gallardo
 */

package es.uma.taw.tarantuvi.controller;

import jakarta.servlet.http.HttpSession;

public class BaseController {
    protected boolean estaAutenticado(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }
}
