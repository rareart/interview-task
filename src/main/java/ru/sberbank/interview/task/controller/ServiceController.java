package ru.sberbank.interview.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.Entity;
import ru.sberbank.interview.task.exception.MissingIdException;
import ru.sberbank.interview.task.service.Service;
import ru.sberbank.interview.task.utils.preloader.Preload;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.application.name}")
public class ServiceController {

    private final Service service;
    private final Preload preloadDB;

    @Autowired
    public ServiceController(Service service, Preload preloadDB) {
        this.service = service;
        this.preloadDB = preloadDB;
    }

    @GetMapping
    public List<Entity> getEntities(
            @RequestHeader(name = "Entities-List") String ids,
            @RequestParam Integer code,
            @RequestParam String sysname) throws MissingIdException {

        if(ids != null){
            return service.getEntitiesByIds(headerStringToLongList(ids));
        }
        return service.getEntitiesByCodeAndSysname(code, sysname);
    }

    @GetMapping("/{sysName}")
    public ResponseEntity<GetListRes> getEntities(@PathVariable String sysName){
        //unnecessary formal checking:
        if (sysName == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        GetListRes list = service.getList(sysName);
        if (list == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    private List<Long> headerStringToLongList(String header) {
        return Arrays
                .stream(header.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
    }

    @PostMapping("/preload")
    public ResponseEntity<?> preloadDbData(@RequestBody String executeFlag){
        if(executeFlag != null && executeFlag.equals("true")){
            preloadDB.execute();
            return new ResponseEntity<>(
                    Collections.singletonMap("status", "preload_executed"),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
