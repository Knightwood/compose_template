package com.kiylx.libx.tools.sonser.gps.convert;

import com.kiylx.libx.tools.sonser.gps.convert.egm96.Geoid;

public class Location {
	// TODO verify if this is meaningful (eg. if this is sufficient for cm accuracy on earth)
	private static final double EPSILON = 0.00000001;

	private double m_lat;
	private double m_lng;

	public Location(double lat, double lng) {
		init(lat, lng);
	}

	private void init(double lat, double lng) {
		m_lat = normalizeLat(lat);
		m_lng = normalizeLong(lng);
	}

	public double getLatitude() {
		return m_lat;
	}

	public double getLongitude() {
		return m_lng;
	}

	private double normalizeLat(double lat) {
		if(lat > 90.0) {
			return normalizeLatPositive(lat);
		}
		else if(lat < -90) {
			return -normalizeLatPositive(-lat);
		}

		return lat;
	}

	private double normalizeLatPositive(double lat) {
		double delta = (lat -  90.0) % 360.0;

		if(delta <= 180.0) {
			lat = 90.0 - delta;
		}
		else {
			lat = delta - 270.0;
		}

		return lat;		
	}

	private double normalizeLong(double lng) {
		lng %= 360.0;

		if(lng >= 0.0) {
			return lng; 
		}
		else {
			return lng + 360;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Location)) {
			return false;
		}

		Location other = (Location) o;

		return Math.abs(getLatitude() - other.getLatitude()) <= EPSILON && Math.abs(getLongitude() - other.getLongitude()) <= EPSILON;

	}

	@Override
	public String toString() {
		return "(" + getLatitude() + "," + getLongitude() + ")";
	}

	public Location floor() {

		double latFloor = Math.floor(getLatitude() / Geoid.LATITUDE_STEP) * Geoid.LATITUDE_STEP;
		double lngFloor = Math.floor(getLongitude() / Geoid.LATITUDE_STEP) * Geoid.LATITUDE_STEP;
		
		return new Location(latFloor, lngFloor);
	}
}