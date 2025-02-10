package com.frankmoley.lil.roomreservationservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashSet;
import java.util.Set;

@Component
public class IdempotencyInterceptor implements HandlerInterceptor {

    Set<String> UUIDS = new HashSet<>();

    /**
     * Interception point before the execution of a handler. Called after
     * HandlerMapping determined an appropriate handler object, but before
     * HandlerAdapter invokes the handler.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending an HTTP error or writing a custom response.
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link AsyncHandlerInterceptor}.
     * <p>The default implementation returns {@code true}.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // check idempotency header

        String uuid = request.getHeader("UUID_");

        if (UUIDS.contains(uuid)) {
            System.out.println("Idempotency Key was Send before : ");
            return false;
        }

        if (uuid != null && !uuid.isEmpty())
            UUIDS.add(uuid);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
