package hello;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

 
@RestController
public class PersonService {

	
	@Autowired PersonRepository personRepository;
	
	@RequestMapping(value = "all" , produces = "application/json",method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Iterable<Person>> getAllKeyWord(
            @RequestHeader HttpHeaders headers, 
            HttpServletRequest request) throws Exception {

		Person person=new Person("abc");

		personRepository.save(person);
		
		return new ResponseEntity(personRepository.findAll(),HttpStatus.FOUND);
	
	}
	
	
}
