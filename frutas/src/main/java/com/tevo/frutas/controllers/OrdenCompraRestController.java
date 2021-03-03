package com.tevo.frutas.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tevo.frutas.models.entity.ordencompra.OrdenCompra;
import com.tevo.frutas.models.services.IUploadFileService;
import com.tevo.frutas.models.services.ordencompra.IOrdenCompraService;

@RestController
@RequestMapping("/api")
public class OrdenCompraRestController {

	@Autowired
	private IOrdenCompraService ordenCompraService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@GetMapping("/orden_compra/autocomplete_empaquetado/{id}/{codigo}")
	public List<String> autocompletado(@PathVariable Long id,@PathVariable String codigo) {
		if (codigo.equals("inexistente")) {
			codigo = "";
		}
		
		return ordenCompraService.autocompleteEmpaquetado(id,codigo);
	}
	
	@GetMapping("/orden_compra/page/{nroOrden}/{columnSort}/{order}/{page}")
	public Page<OrdenCompra> index(@PathVariable String nroOrden,
			@PathVariable String columnSort,
			@PathVariable Integer order,
			@PathVariable Integer page) {
		
		if (nroOrden.equals("nroOrden")) {
			nroOrden = "";
		}	
		
		return ordenCompraService.findPageAndSort(nroOrden, columnSort, order, page);
	}
	
	@GetMapping("/orden_compra/page2")
	public Page<OrdenCompra> index2(
			@RequestParam(value = "nroOrden"		,required = false,defaultValue = "") String nroOrden,
			@RequestParam(value = "columnSort"		,required = false,defaultValue = "id") String columnSort,
			@RequestParam(value = "order"			,required = false,defaultValue = "0") int order,
			@RequestParam(value = "page"			,required = false,defaultValue = "0") int page,
			@RequestParam(value = "fechaDesde"		,required = false,defaultValue = "01012020") @DateTimeFormat(pattern="ddMMyyyy") Date fechaDesde,
			@RequestParam(value = "fechaHasta"		,required = false,defaultValue = "01013000") @DateTimeFormat(pattern="ddMMyyyy") Date fechaHasta,
			@RequestParam(value = "comprador"		,required = false,defaultValue = "") String comprador,
			@RequestParam(value = "proveedorId"		,required = false,defaultValue = "0") Long proveedorId,
			@RequestParam(value = "tipoCompraId"	,required = false,defaultValue = "0") Long tipoCompraId,
			@RequestParam(value = "tipoOrdenId"		,required = false,defaultValue = "0") Long tipoOrdenId,
			@RequestParam(value = "estadoOrdenId"	,required = false,defaultValue = "0") Long estadoOrdenId) {
		return ordenCompraService.findAllPageAndSort2(nroOrden, fechaDesde, fechaHasta, comprador,
													  proveedorId, tipoCompraId, tipoOrdenId, estadoOrdenId,
													  columnSort, order, page);
	}	
	
	@GetMapping("/orden_compra/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		OrdenCompra ordenCompra = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			ordenCompra = ordenCompraService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (ordenCompra == null) {
			response.put("mensaje", "La orden de compra no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrdenCompra>(ordenCompra, HttpStatus.OK); 
	}
	
	@PostMapping("/orden_compra")
	public ResponseEntity<?> create(@Valid @RequestBody OrdenCompra ordenCompra, BindingResult result) throws ParseException {
		OrdenCompra ordenCompraNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			String nroOrden = obtenerNuevoNro();		
			ordenCompra.setNroOrden(nroOrden);
			ordenCompraNew = ordenCompraService.save(ordenCompra);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Orden de compra registrada exitosamente");
		response.put("ordenCompra", ordenCompraNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}	
	
	@PutMapping("/orden_compra/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody OrdenCompra ordenCompra, BindingResult result, @PathVariable Long id) {
		OrdenCompra ordenCompraActual = ordenCompraService.findById(id);
		OrdenCompra ordenCompraUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (ordenCompraActual == null) {
			response.put("mensaje", "Error, no se pudo editar: La orden de compra no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			ordenCompraActual.setNroOrden(ordenCompra.getNroOrden());
			ordenCompraActual.setComprador(ordenCompra.getComprador());
			ordenCompraActual.setProveedor(ordenCompra.getProveedor());
			ordenCompraActual.setTipoCompra(ordenCompra.getTipoCompra());
			ordenCompraActual.setTipoOrden(ordenCompra.getTipoOrden());
			ordenCompraActual.setMoneda(ordenCompra.getMoneda());
			ordenCompraActual.setIndIgv(ordenCompra.getIndIgv());
			ordenCompraActual.setTotal(ordenCompra.getTotal());
			ordenCompraActual.setLugarCompra(ordenCompra.getLugarCompra());
			ordenCompraActual.setFechaCompra(ordenCompra.getFechaCompra());
			ordenCompraActual.setDetalle(ordenCompra.getDetalle());
			ordenCompraActual.setEstadoOrdenCompra(ordenCompra.getEstadoOrdenCompra());
			
			ordenCompraUpdate = ordenCompraService.save(ordenCompraActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Orden de Compra actualizado exitosamente");
		response.put("ordenCompra", ordenCompraUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	public String obtenerNuevoNro() throws ParseException {
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR);
		
		String fechaI = "01/01/".concat(year.toString());
		String fechaF = "31/12/".concat(year.toString());
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIni =  format.parse(fechaI);
		
		Date fechaFin =  format.parse(fechaF);	

		String nroOrden = ordenCompraService.obtenerUltimoCodigo(fechaIni, fechaFin);
		
		String parteCadena = "";
		Integer nroOr = Integer.parseInt(nroOrden.substring(0, 4)) + 1;
		parteCadena = "0000".concat(nroOr.toString());
		
		String nroOrdenNuevo = "";
		year = year - 2000;
		nroOrdenNuevo = parteCadena.substring(parteCadena.length() - 4).concat("-").concat(year.toString());
		
		return nroOrdenNuevo;
	}
	
//	@PostMapping("/orden_compra/embalaje/upload")
//	public ResponseEntity<?> upload(@RequestParam("archivos[]") MultipartFile archivos[], @RequestParam("ids[]") Long ids[], @RequestParam("idOC") Long idOC) {
//		logger.info("llegando " + idOC);
//		
//		Map<String, Object> response = new HashMap<>();
//		OrdenCompra ordenCompra = ordenCompraService.findById(idOC);
//		
//		for (int i = 0; i < ordenCompra.getDetalle().size(); i++) {
//			Boolean fotoEncontrada = false;
//			for (int j = 0; j < ids.length; j++) {
//				if (!archivos[j].isEmpty() && ids[j] == ordenCompra.getDetalle().get(i).getId()) {
//					String nombreArchivo = null;
//					
//					try {
//						nombreArchivo = uploadFileService.copiar(archivos[j]);
//						fotoEncontrada = true;
//					} catch (IOException e) {
//						response.put("mensaje", "Error al subir la imagen");
//						response.put("error", e.getMessage().concat(": ").concat(e.getCause().toString()));
//						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//					}
//					
//					String nombreFotoAnterior = ordenCompra.getDetalle().get(i).getFotoEmbalaje();
//					uploadFileService.eliminar(nombreFotoAnterior);
//					
//					ordenCompra.getDetalle().get(i).setFotoEmbalaje(nombreArchivo);
//				}
//			}
//				
//			if (!fotoEncontrada) {
//				if (ordenCompra.getDetalle().get(i).getFotoEmbalaje() != null && ordenCompra.getDetalle().get(i).getFotoEmbalaje().length() > 0) {
//					uploadFileService.eliminar(ordenCompra.getDetalle().get(i).getFotoEmbalaje());			
//				}
//				ordenCompra.getDetalle().get(i).setFotoEmbalaje("");
//			}
//		}
//		
//		ordenCompraService.save(ordenCompra);	
//		response.put("ordenCompra", ordenCompra);
//		
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("archivos[]") MultipartFile archivos[], @RequestParam("ids[]") Long ids[], @RequestParam("idOC") Long idOC) {
		
		Map<String, Object> response = new HashMap<>();
		OrdenCompra ordenCompra = ordenCompraService.findById(idOC);
		
		for (int i = 0; i < ordenCompra.getDetalle().size(); i++) {
			Boolean fotoEncontrada = false;
			for (int j = 0; j < ids.length; j++) {
				if (!archivos[j].isEmpty() && ids[j] == ordenCompra.getDetalle().get(i).getId()) {
					String nombreArchivo = null;
					
					try {
						nombreArchivo = uploadFileService.copiar(archivos[j]);
						fotoEncontrada = true;
					} catch (IOException e) {
						response.put("mensaje", "Error al subir la imagen");
						response.put("error", e.getMessage().concat(": ").concat(e.getCause().toString()));
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					
					//String nombreFotoAnterior = ordenCompra.getDetalle().get(i).getFotoEmbalaje();
					//uploadFileService.eliminar(nombreFotoAnterior);
					
					ordenCompra.getDetalle().get(i).setFotoEmbalaje(nombreArchivo);
				}
			}
				
			if (!fotoEncontrada) {
				if (ordenCompra.getDetalle().get(i).getFotoEmbalaje() != null && ordenCompra.getDetalle().get(i).getFotoEmbalaje().length() > 0) {
					uploadFileService.eliminar(ordenCompra.getDetalle().get(i).getFotoEmbalaje());			
				}
				ordenCompra.getDetalle().get(i).setFotoEmbalaje("");
			}
		}
		
		ordenCompraService.save(ordenCompra);	
		response.put("ordenCompra", ordenCompra);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("orden_compra/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Resource recurso = null;
		try {
			recurso = uploadFileService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
//	@GetMapping(path = "orden_compra/subscription", produces = "text/event-stream")
//	public SseEmitter subscribe() {
//		SseEmitter emitter = new SseEmitter(-1L);
//		sseService.add(emitter);
//        emitter.onCompletion(() -> sseService.remove(emitter));
//        return emitter;
//	}
	
//	@GetMapping("orden_compra/notification")
//    public String produce() throws JsonGenerationException, JsonMappingException, IOException {
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		String json = ow.writeValueAsString(sseService);
//		logger.info(json);
//        sseService.getSsEmitters().forEach((SseEmitter emitter) -> {
//            try {
//                emitter.send("", MediaType.APPLICATION_JSON);
//            } catch (IOException e) {
//                emitter.complete();
//                sseService.remove(emitter);
//                e.printStackTrace();
//            }
//        });
//        return "";
//    }
}
