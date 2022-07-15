package pl.uzi.psspringbootstarttutorial.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pl.uzi.psspringbootstarttutorial.models.Session;
import pl.uzi.psspringbootstarttutorial.models.Speaker;
import pl.uzi.psspringbootstarttutorial.repositories.SessionRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsApi {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionsApi(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/all")
    public List<Session> getSessions(){
        return sessionRepository.findAll();
    }
    @GetMapping()
    public Session get(@RequestParam Long id){
        return sessionRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @PostMapping("/add")
    public Session get(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @DeleteMapping("/erase")
    public void delete(@RequestParam Long id){
        sessionRepository.deleteById(id);
    }

    @PutMapping("/put")
    public int update(@RequestParam Long id, @RequestBody Session session){
        try {
            Session newSession = sessionRepository.findById(id).orElseThrow(NoSuchElementException::new);
            BeanUtils.copyProperties(session,newSession,"session_id");
            sessionRepository.saveAndFlush(newSession);
            return HttpServletResponse.SC_ACCEPTED;
        }catch (NoSuchElementException e){
            System.err.println("No such element!");
        }

        return HttpServletResponse.SC_NO_CONTENT;
    }

    @PatchMapping("/patch")
    public int postObject(@RequestParam Long id, @RequestBody Session session){
        try {
            Session newSession = sessionRepository.findById(id).orElseThrow(NoSuchElementException::new);
            boolean needUpdate = false;

            if(StringUtils.hasLength(session.getSession_name())){
                newSession.setSession_name(session.getSession_name());
                needUpdate = true;
            }
            if(StringUtils.hasLength(session.getSession_description())){
                newSession.setSession_description(session.getSession_description());
                needUpdate = true;
            }
            if(StringUtils.hasLength(String.valueOf(session.getSession_length()))){
                newSession.setSession_length(session.getSession_length());
                needUpdate = true;
            }
            if(StringUtils.hasLength(String.valueOf(session.getSpeakers()))){
                newSession.setSpeakers(session.getSpeakers());
                needUpdate = true;
            }
            if(needUpdate){
                sessionRepository.saveAndFlush(newSession);
                return HttpServletResponse.SC_ACCEPTED;
            }else
                return HttpServletResponse.SC_NO_CONTENT;
        }catch (NoSuchElementException e){
            System.err.println("No such element!");
        }
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
