package tcc.tesseract.microservice.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@RestController
@RequestMapping("/ocr")
public class OcrController {

	@ApiOperation(value = "Retorna texto lido na imagem enviada")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retornou o texto contido na imagem"),
            @ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 405, message = "GET não suportado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
	@PostMapping("/extrair")
	public ResponseEntity<String> extrair(@RequestParam(name = "file") MultipartFile file) throws Exception {

		String resultado = "";

		BufferedImage img = ImageIO.read(file.getInputStream());

		ITesseract tesseract = new Tesseract();

		tesseract.setDatapath("src/main/resources/tess/tessdata");
		tesseract.setLanguage("por");

		try {
			resultado = tesseract.doOCR(img);
		} catch (TesseractException e) {
			throw new Exception("Erro na tentativa de ler o arquivo");
		}
		return ResponseEntity.ok(resultado);
	}

}
