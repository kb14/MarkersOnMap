package apt.kangkan;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.List;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class AddrMarkrsonMapActivity extends MapActivity {
	
	private static final String TAG = "myActivity";
		//Global Declarations
		MapView myMap = null;
		//MyLocationOverlay myOverlay = null;       //Not Used
		MapController myController;
		
		@Override
		protected boolean isRouteDisplayed() {
			return false;
			}
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	    
	        // Get a reference to the MapView 
	        myMap = (MapView)findViewById(R.id.myMapView);
	        
	        //Get the Map View's Controller
	        myController = myMap.getController();
	        
	        //Zoom and zoom controls
	        myController.setZoom(17);
	        myMap.setBuiltInZoomControls(true);
	        
	        //Drawable and marker stuff from 
	       /* Drawable marker=getResources().getDrawable(R.drawable.marker);
	        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
	                                marker.getIntrinsicHeight());*/
	        
	        List<Overlay> mapOverlays = myMap.getOverlays();
	        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
	        MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);
	        //System.out.println("error la hahaha");
	        //Read from file Stuff
	        Scanner fileScanner = new Scanner(getResources().openRawResource(R.raw.list));
	        while(fileScanner.hasNext()){
	        	String str1 = fileScanner.nextLine();
	        	StringTokenizer str2 = new StringTokenizer(str1,";");
	        	//System.out.println("error la hahaha");
	        	while(str2.hasMoreTokens()){
	        		String name = str2.nextToken();
	        		String addr = str2.nextToken();
	        		//System.out.println("error la hahaha");
	        		double lat = 0;
	        		double lon = 0;
	        		//Geocoder fwdGeocoder= new Geocoder(this, Locale.getDefault());
	        		Geocoder fwdGeocoder= new Geocoder(this);
	        		List<Address> addressList = null;
	        		//System.out.println(lat);
	        		System.out.println(addr);
					try {
						//System.out.println("error la hahaha");
						addressList = fwdGeocoder.getFromLocationName(addr, 5);
						if (addressList == null)
					    {
							Log.d(TAG,"Latitude and longitude not found");
					    }
						
					} catch (IOException e) {}
	        		Address address = addressList.get(0);
	        		if(address.hasLatitude() && address.hasLongitude()){
	        		     lat = address.getLatitude();
	        		     lon = address.getLongitude();
	        		     
	        		}
	        		System.out.println(lat);
	        		System.out.println(lon);
	        		GeoPoint point = getPoint(lat,lon);
	        		OverlayItem overlayitem = new OverlayItem(point, name, addr);
	        		itemizedoverlay.addOverlay(overlayitem);
	        	}
	        }
	        mapOverlays.add(itemizedoverlay);
	    
	    }
		
		@Override
		  public boolean onKeyDown(int keyCode, KeyEvent event) {
		   
			if (keyCode == KeyEvent.KEYCODE_Z) {
		      myMap.displayZoomControls(true);
		      return(true);
		    }
		    
		    return(super.onKeyDown(keyCode, event));
		  }
		
		private GeoPoint getPoint(double lat, double lon) {
		    return(new GeoPoint((int)(lat*1000000.0),
		                          (int)(lon*1000000.0)));
		  }
	}