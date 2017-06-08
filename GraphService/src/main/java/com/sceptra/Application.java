
package com.sceptra;

import com.sceptra.finder.ApacheLibraryDesc;
import com.sceptra.finder.TechTermDesc;
import com.sceptra.finder.WikiDesc;
import com.sceptra.processor.Tagger;
import com.sceptra.processor.requirement.KeywordMap;
import com.sceptra.requestor.HTTPRequester;
import com.sceptra.requestor.NLPServiceRequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

	private final static Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	NLPServiceRequester getNLPServiceRequester(){

		return new NLPServiceRequester();
	}

	@Bean
	TechTermDesc getTechTermDesc(){

		return new TechTermDesc();
	}

	@Bean
	HTTPRequester getHTTPRequester(){

		return new HTTPRequester();
	}

	@Bean
	WikiDesc getWikiDesc(){

		return new WikiDesc();
	}

	@Bean
	KeywordMap getKeywordMap(){

		return new KeywordMap();
	}

	@Bean
	Tagger getTagger(){

		return new Tagger();
	}

	@Bean
	ApacheLibraryDesc getApacheLibraryDesc(){
		return new ApacheLibraryDesc();
	}
}
