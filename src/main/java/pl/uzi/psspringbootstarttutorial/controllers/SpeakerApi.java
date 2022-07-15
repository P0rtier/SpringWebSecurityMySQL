package pl.uzi.psspringbootstarttutorial.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pl.uzi.psspringbootstarttutorial.models.Speaker;
import pl.uzi.psspringbootstarttutorial.repositories.SpeakerRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerApi {

    private final SpeakerRepository speakerRepository;

    @Autowired
    public SpeakerApi(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @GetMapping("/all")
    public List<Speaker> getAllSpeakers(){
        return speakerRepository.findAll();
    }

    @GetMapping
    public Speaker getById(@RequestParam Long id){
        return speakerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @PostMapping("/add")
    public Speaker get(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @PutMapping("/put")
    public int putSpeaker(@RequestParam Long id, @RequestBody Speaker speaker){
            Speaker speaker1 = new Speaker();
            speaker1.setSpeaker_id(id);
            BeanUtils.copyProperties(speaker,speaker1,"speaker_id");
            speakerRepository.saveAndFlush(speaker1);
            return HttpServletResponse.SC_ACCEPTED;
    }

    @DeleteMapping("/erase")
    public void deleteSpeaker(@RequestParam Long id){
        speakerRepository.deleteById(id);
    }

    @PatchMapping("/patch")
    public int patchSpeaker(@RequestParam Long id, @RequestBody Speaker speaker){
        try {
            Speaker speaker1 = speakerRepository.findById(id).orElseThrow(NoSuchElementException::new);
            boolean needUpdate = false;

            if(StringUtils.hasLength(speaker.getSpeaker_bio())){
                speaker1.setSpeaker_bio(speaker.getSpeaker_bio());
                needUpdate = true;
            }
            if(StringUtils.hasLength(speaker.getFirst_name())){
                speaker1.setFirst_name(speaker.getFirst_name());
                needUpdate = true;
            }
            if(StringUtils.hasLength(speaker.getLast_name())){
                speaker1.setLast_name(speaker.getLast_name());
                needUpdate = true;
            }
            if(StringUtils.hasLength(speaker.getTitle())){
                speaker1.setTitle(speaker.getTitle());
                needUpdate = true;
            }
            if(StringUtils.hasLength(speaker.getCompany())){
                speaker1.setCompany(speaker.getCompany());
                needUpdate = true;
            }
            if(StringUtils.hasLength(String.valueOf(speaker.getSessions()))){
                speaker1.setSessions(speaker.getSessions());
                needUpdate = true;
            }
            if(StringUtils.hasLength(String.valueOf(speaker.getSpeaker_photo()))){
                speaker1.setSpeaker_photo(speaker.getSpeaker_photo());
                needUpdate = true;
            }
            if(needUpdate){
                speakerRepository.saveAndFlush(speaker1);
                return HttpServletResponse.SC_ACCEPTED;
            }
        }catch (NoSuchElementException e){
            System.err.println("No such element!");
        }
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
