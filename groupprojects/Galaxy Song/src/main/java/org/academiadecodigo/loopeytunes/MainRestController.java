package org.academiadecodigo.loopeytunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")

public class MainRestController {

    private TempDB tempDB;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/senddog",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dog> sendDog() {

        return new ResponseEntity<>(tempDB.getDog(), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/dogList/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> sendId(@PathVariable Integer id) {

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Autowired
    public void setTempDB(TempDB tempDB) {
        this.tempDB = tempDB;
    }
}
