package br.com.tcc.guardia.rosa.business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessorBusiness {

	private SimpMessagingTemplate template;
	
	@Autowired
	public ProcessorBusiness(SimpMessagingTemplate template) { this.template = template; }
	
	@Async
	public void execute() {
		try {
			Thread.sleep(2000);
			template.convertAndSend("/statusProcessor", gerarMensagem(1));
			Thread.sleep(2000);
			template.convertAndSend("/statusProcessor", gerarMensagem(2));
			Thread.sleep(2000);
			template.convertAndSend("/statusProcessor", gerarMensagem(3));
		} catch (Exception e) {
		}
	}

	private Object gerarMensagem(int etapa) {
		return String.format("Executada a etapa %s às %s", etapa, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
	}
	
}
