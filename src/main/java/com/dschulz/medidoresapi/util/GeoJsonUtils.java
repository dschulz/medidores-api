package com.dschulz.medidoresapi.util;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;

import com.dschulz.medidoresapi.domain.Lectura;
import com.dschulz.medidoresapi.domain.Medidor;
import com.dschulz.medidoresapi.domain.Posicion;

public class GeoJsonUtils {
	
	public static FeatureCollection generarDesdeMedidores(List<Medidor> medidores) {
		
		FeatureCollection featureCollection = new FeatureCollection();
		
		
		for (Medidor med : medidores) {
	
			Posicion mPos = med.getPosicion();
			if(mPos == null) {
				continue;
			}
			
			Feature feature = new Feature();
			
			Map<String, Object> props = new HashMap<>();
			
			
			DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
			
		
			props.put("codigo", med.getCodigo());
			
			//props.put("titular", "Nombre del abonado");
			
			props.put("organizacion", med.getOrganizacion().getNombre());
			props.put("numero", med.getNumero().toString());
			props.put("registradoEn",  fmt.format(med.getCreado()));		
			props.put("registradoPor",  med.getCreadoPor());
			
			
			
			Double lng = mPos.getLongitud();
			Double lat = mPos.getLatitud();
			Double alt = mPos.getAltitud();
			
			LngLatAlt p = new LngLatAlt(lng,lat,alt);
	    	Point geometry = new Point(p);
			
	    	feature.setProperties(props);
			feature.setGeometry(geometry);		
			featureCollection.add(feature);
		
			
			
		}
		
				
		return featureCollection;
		
	}

	
	public static FeatureCollection generarDesdeLecturas(List<Lectura> lecturas) {
		
		FeatureCollection featureCollection = new FeatureCollection();
		
		
		for (Lectura lectura : lecturas) {
	
			Posicion mPos = lectura.getPosicion();
			if(mPos == null) {
				continue;
			}
			
			Feature feature = new Feature();
			
			Map<String, Object> props = new HashMap<>();
			
			
			DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
			
		
			props.put("codigo", lectura.getCodigo());
			props.put("contador", lectura.getContador().toString());
			props.put("registradoEn",  fmt.format(lectura.getCreado()));		
			props.put("registradoPor",  lectura.getCreadoPor());
			
			
			Double lng = mPos.getLongitud();
			Double lat = mPos.getLatitud();
			Double alt = mPos.getAltitud();
			
			LngLatAlt p = new LngLatAlt(lng,lat,alt);
	    	Point geometry = new Point(p);
			
	    	feature.setProperties(props);
			feature.setGeometry(geometry);		
			featureCollection.add(feature);
		
			
			
		}
		
				
		return featureCollection;
		
	}
	
	
}