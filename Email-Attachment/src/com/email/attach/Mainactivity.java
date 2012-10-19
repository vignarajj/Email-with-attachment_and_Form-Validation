package com.email.attach;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Mainactivity extends Activity {
	ArrayList<String> str = new ArrayList<String>();
	private Boolean firstLvl = true;
	private static final String TAG = "F_PATH";
	private Item[] fileList;
	private File path = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "");
	private String chosenFile;
	private static final int DIALOG_LOAD_FILE = 1000;
	ListAdapter adapter;
	EditText pincode,cname,cloc,ploc,cctc,ectc,cpro,epro,exp;
	Button submit,reset,browse;
	Spinner state,district;
	TextView filename;
	String dis[]={"Select District","Adilabad","Anantapur","Chittoor","East Godavari","Guntur","Hyderabad","YSR district","Karimnagar","Khammam","Krishna (N T R District)","Kurnool","Mahbubnagar","Medak","Nalgonda","Nellore","Nizamabad","Prakasam","Rangareddi","Srikakulam","Vishakhapatnam","Vizianagaram","Warangal","West Godavari",
		       "Anjaw","Changlang","East Kameng","East Siang”,”Lohit”,”Lower Subansiri”,”Papum Pare”,”Tawang”,”Tirap”,”Dibang Valley”,”Upper Siang","Upper Subansiri","West Kameng","West Siang","Barpeta","Bongaigaon","Cachar","Darrang","Dhemaji","Dhubri","Dibrugarh","Goalpara","Golaghat","Hailakandi","Jorhat","Karbi Anglong",
		       "Karimganj","Kokrajhar","Lakhimpur","Marigaon","Nagaon","Nalbari","Dima Hasao","Sibsagar","Sonitpur","Tinsukia","Araria","Arwal","Aurangabad","Banka","Begusarai","Bhagalpur","Bhojpur","Buxar","Darbhanga","East Champaran","Gaya","Gopalganj","Jamui","Jehanabad","Khagaria","Kishanganj","Kaimur","Katihar","Lakhisarai",
		       "Madhubani","Munger","Madhepura","Muzaffarpur","Nalanda","Nawada","Patna","Purnia","Rohtas","Saharsa","Samastipur","Sheohar","Sheikhpura","Saran","Sitamarhi","Supaul","Siwan","Vaishali(Akshay)","West Champaran","Bastar","Bijapur","Bilaspur","Dantewada","Dhamtari","Durg","Jashpur","Janjgir-Champa","Korba","Koriya",
		       "Kanker","Kawardha","Mahasamund","Narayanpur","Raigarh","Rajnandgaon","Raipur","Surajpur","North Goa","South Goa","Central Delhi","East Delhi","New Delhi","North Delhi","North East Delhi","North West Delhi","South Delhi","South West Delhi","West Delhi","Ahmedabad","Amreli District","Anand","Banaskantha","Bharuch",
		       "Bhavnagar","Dahod","The Dangs","Gandhinagar","Jamnagar","Junagadh","Kutch","Kheda","Mehsana","Narmada","Navsari","Patan","Panchmahal","Porbandar","Rajkot","Sabarkantha","Surendranagar","Surat","Vadodara","Valsad","Ambala","Bhiwani","Faridabad","Fatehabad","Gurgaon","Hissar","Jhajjar","Jind","Karnal","Kaithal",
		       "Kurukshetra","Mahendragarh","Mewat","Panchkula","Panipat","Rewari","Rohtak","Sirsa","Sonepat","Yamuna Nagar","Palwal","Bilaspur","Chamba","Hamirpur","Kangra","Kinnaur","Kulu","Lahaul and Spiti","Mandi","Shimla","Sirmaur","Solan","Una","Anantnag","Badgam","Bandipore","Baramula","Doda","Ganderbal","Jammu","Kargil",
	       	   "Kathua","Kishtwar","Kupwara","Kulgam","Leh","Poonch","Pulwama","Rajauri","Ramban","Reasi","Samba","Shopian","Srinagar","Udhampur district","Bokaro","Chatra","Deoghar","Dhanbad","Dumka","East Singhbhum","Garhwa","Giridih","Godda","Gumla","Hazaribag","Koderma","Lohardaga","Pakur","Palamu","Ranchi","Sahibganj",
		       "Seraikela-Kharsawan","West Singhbhum","Ramgarh","Bidar","Belgaum","Bijapur","Bagalkot","Bellary","Bangalore Rural District","Bangalore Urban district","Chamarajnagar","Chikkodi","Chikmagalur","Chitradurga","Davanagere","Dharwad","Dakshina Kannada","Gadag","Gulbarga","Hassan","Haveri District","Kodagu","Kolar",
		       "Koppal","Mandya","Mysore","Raichur","Shimoga","Tumkur","Udupi","Uttara","Kannada","Ramanagara","Chikballapur","Yadgir","Alappuzha","Ernakulam","Idukki","Kollam","Kannur","Kasaragod","Kottayam","Kozhikode","Malappuram","Palakkad","Pathanamthitta","Thrissur","Thiruvananthapuram","Wayanad","Alirajpur","Anuppur",
		       "Ashok Nagar","Balaghat","Barwani","Betul","Bhind","Bhopal","Burhanpur","Chhatarpur","Chhindwara","Damoh","Datia","Dewas","Dhar","Dindori","Guna","Gwalior","Harda","Hoshangabad","Indore","Jabalpur","Jhabua","Katni","Khandwa (East Nimar)","Khargone (West Nimar)","Mandla","Mandsaur","Morena","Narsinghpur","Neemuch",
		       "Panna","Rewa","Rajgarh","Ratlam","Raisen","Sagar","Satna","Sehore","Seoni","Shahdol","Shajapur","Sheopur","Shivpuri","Sidhi","Singrauli","Tikamgarh","Ujjain","Umaria","Vidisha","Ahmednagar","Akola","Amravati","Aurangabad","Buldhana","Chandrapur","Dhule","Gadchiroli","Gondia","Hingoli","Jalgaon","Jalna",
		       "Kolhapur","Latur","Mumbai City","Mumbai suburban","Nandurbar","Nanded","Nagpur","Nashik","Osmanabad","Parbhani","Pune","Raigad","Ratnagiri","Sindhudurg","Sangli","Solapur","Thane","Wardha","Washim","Yavatmal","Bishnupur","Churachandpur","Chandel","Imphal East","Senapati","Tamenglong","Thoubal","Ukhrul","Imphal West",
		       "East Garo Hills","East Khasi Hills","Jaintia Hills","Ri Bhoi","South Garo Hills","West Garo Hills","West Khasi Hills","Aizawl","Champhai","Kolasib","Lawngtlai","Lunglei","Mamit","Saiha","Serchhip","Dimapur","Kohima","Mokokchung","Mon","Phek","Tuensang","Wokha","Zunheboto","Angul","Boudh (Bauda)","Bhadrak",
		       "Balangir","Bargarh (Baragarh)","Balasore","Cuttack","Debagarh (Deogarh)","Dhenkanal","Ganjam","Gajapati","Jharsuguda","Jajpur","Jagatsinghpur","Khordha","Kendujhar (Keonjhar","Kalahandi","Kandhamal","Koraput","Kendrapara","Malkangiri","Mayurbhanj","Nabarangpur","Nuapada","Nayagarh","Puri","Rayagada","Sambalpur",
		       "Subarnapur (Sonepur)","Sundargarh (Sundergarh)","Bathinda","Barnala","Amritsar","Firozpur","Faridkot","Fatehgarh Sahib","Fazilka district","Gurdaspur","Hoshiarpur","Jalandhar","Kapurthala","Ludhiana","Mansa","Moga","Mukatsar","Pathankot","Patiala","Rupnagar","Sahibzada Ajit Singh Nagar","Sangrur","Shahid Bhagat Singh Nagar",
		       "Tarn Taran","Ajmer","Alwar","Bikaner","Barmer","Banswara","Bharatpur","Baran","Bundi","Bhilwara","Churu","Chittorgarh","Dausa","Dholpur","Dungapur","Ganganagar","Hanumangarh","Jhunjhunu","Jalore","Jodhpur","Jaipur","Jaisalmer","Jhalawar","Karauli","Kota","Nagaur","Pali","Pratapgarh","Rajsamand","Sikar","Sawai Madhopur","Sirohi",
		       "Tonk","Udaipur","East Sikkim","North Sikkim","South Sikkim","West Sikkim","Ariyalur","Chennai","Coimbatore","Cuddalore","Dharmapuri","Dindigul","Erode","Kanchipuram","Kanyakumari","Karur","Madurai","Nagapattinam","Nilgiris","Namakkal","Perambalur","Pudukkottai","Ramanathapuram","Salem","Sivaganga","Tiruppur","Tiruchirappalli",
		       "Theni","Tirunelveli","Thanjavur","Thoothukudi","Thiruvallur","Thiruvarur","Tiruvannamalai","Vellore","Viluppuram","Virudhunagar","Dhalai","North Tripura","South Tripura","West Tripura","Agra","Allahabad","Aligarh","Ambedkar Nagar","Auraiya","Azamgarh","Barabanki","Budaun","Bagpat","Bahraich","Bijnor","Ballia","Banda","Balrampur",
		       "Bareilly","Basti","Bulandshahar","Chandauli","Chhatrapati Shahuji Maharaj Nagar","Chitrakoot","Deoria","Etah","Kanshi Ram Nagar","Etawah","Farrukhabad","Fatehpur","Faizabad","Gautam Buddha Nagar","Gonda","Ghazipur","Gorakhpur","Ghaziabad","Hamirpur","Hardoi","Mahamaya Nagar","Jhansi","Jalaun","Jyotiba Phule Nagar","Jaunpur District",
		       "Ramabai Nagar (Kanpur Dehat)","Kannauj","Kanpur Nagar","Kaushambi","Kushinagar","Lalitpur","Lakhimpur-Kheri","Lucknow","Mau","Meerut","Maharajganj","Mahoba","Mirzapur","Moradabad","Mainpuri","Mathura","Muzaffarnagar","Panchsheel Nagar district Hapur","Pilibhit","Pratapgarh","Rampur","Raebareli","Saharanpur","Sitapur","Shahjahanpur",
		       "Sant Kabir Nagar","Siddharthnagar","Sonbhadra","Sant Ravidas Nagar","Sultanpur","Sultanpur","Shravasti","Unnao","Varanasi","Almora|","Bageshwar","Chamoli","Champawat","Dehradun","Haridwar","Nainital","Pauri Garhwal","Pithoragarh","Rudraprayag","Tehri Garhwal","Udham Singh Nagar","Uttarkashi","Birbhum","Bankura","Bardhaman","Darjeeling",
		       "Dakshin Dinajpur","Hooghly","Howrah","Jalpaiguri","Cooch Behar","Kolkata","Malda","Paschim Medinipur","Purba Medinipur","Murshidabad","Nadia","North  Parganas","South  Parganas","Purulia","Uttar Dinajpur",
};
	String stat[]={"Select State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Punjab","Rajasthan","Sikkim","Tamil Nadu","Tripura","Uttar Pradesh",
			       "Uttarakhand","West Bengal","Andaman and Nicobar","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Lakshadweep","National Capital Territory of Delhi","Puducherry","Delhi"};
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	state=(Spinner)findViewById(R.id.state);
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(Mainactivity.this,android.R.layout.simple_spinner_item,stat);
    state.setAdapter(adapter);
	district=(Spinner)findViewById(R.id.district);
	ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Mainactivity.this,android.R.layout.simple_spinner_item,dis);
	district.setAdapter(adapter1);
	pincode=(EditText)findViewById(R.id.pincode);
	cname=(EditText)findViewById(R.id.cname);
	cloc=(EditText)findViewById(R.id.clocation);
	ploc=(EditText)findViewById(R.id.plocation);
	cctc=(EditText)findViewById(R.id.cctc);
	ectc=(EditText)findViewById(R.id.ectc);
	cpro=(EditText)findViewById(R.id.cprofile);
	epro=(EditText)findViewById(R.id.eprofile);
	exp=(EditText)findViewById(R.id.exper);
	submit=(Button)findViewById(R.id.submit);
	reset=(Button)findViewById(R.id.reset);
	filename=(TextView)findViewById(R.id.filename);
	browse=(Button)findViewById(R.id.browse);
	submit.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			int pos1=state.getSelectedItemPosition();
//			int pos2=district.getSelectedItemPosition();
			String s1=state.getSelectedItem().toString();
			String s2=district.getSelectedItem().toString();
			String s3=pincode.getText().toString();
			String s4=cname.getText().toString();
			String s5=cloc.getText().toString();
			String s6=cctc.getText().toString();
			String s7=ectc.getText().toString();
			String s8=cpro.getText().toString();
			String s9=epro.getText().toString();
			String s10=exp.getText().toString();
			String s11=ploc.getText().toString();
			String all="State :"+s1+"\n"+"District :"+s2+"\n"+"Pincode :"+s3+"\n"+"Candidate Name :"+s4+"\n"
					+"Current Location :"+s5+"\n"+"Preferred Location :"+s11+"\n"+"Current CTC :"+s6+
					"\n"+"Expected CTC :"+s7+"\n"+"Current Profile :"+s8+"\n"+"Expected Profile :"+s9+"\n"+"Experience :"+s10;
			if(state.getSelectedItemPosition()==0)
			{
				Toast.makeText(Mainactivity.this, "Select your state", Toast.LENGTH_SHORT).show();
			}
			else if(district.getSelectedItemPosition()==0)
			{
				Toast.makeText(Mainactivity.this, "Select your district", Toast.LENGTH_SHORT).show();
			}
			else if(pincode.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter the Pincode", Toast.LENGTH_SHORT).show();
			}
			else if(cname.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();
			}
			else if(cloc.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter your Current Location", Toast.LENGTH_SHORT).show();
			}
			else if(cctc.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter your Current CTC", Toast.LENGTH_SHORT).show();
			}
			else if(ectc.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter your Expected CTC", Toast.LENGTH_SHORT).show();
			}
			else if(cpro.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter your Current Profile", Toast.LENGTH_SHORT).show();
			}
			else if(exp.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Enter your Experience", Toast.LENGTH_SHORT).show();
			}
			else if(filename.getText().length()==0)
			{
				Toast.makeText(Mainactivity.this, "Upload your file", Toast.LENGTH_SHORT).show();
			}
			else
			{
				File path1=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),chosenFile);
				Uri uri = Uri.fromFile(path1);
				Toast.makeText(Mainactivity.this,all,Toast.LENGTH_SHORT).show();
				Intent email1 = new Intent(Intent.ACTION_SEND);
			      email1.setType("text/html");
			      email1.putExtra(Intent.EXTRA_EMAIL  , new String[]{"yourmail@com"});
			      email1.putExtra(Intent.EXTRA_SUBJECT, "Subject:");
			      email1.putExtra(Intent.EXTRA_TEXT   , all);
			      email1.putExtra(Intent.EXTRA_STREAM,uri);
			      try {
			         startActivity(Intent.createChooser(email1, "Send mail..."));
			      } catch (android.content.ActivityNotFoundException ex) {
			         Toast.makeText(Mainactivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

		}
			}
		}
	});
	reset.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			state.setSelection(0);
			district.setSelection(0);
			pincode.setText("");
			cname.setText("");
			cloc.setText("");
			cctc.setText("");
			ectc.setText("");
			cpro.setText("");
			epro.setText("");
			exp.setText("");
			ploc.setText("");
			filename.setText("");
		}
	});
	browse.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			loadFileList();
			showDialog(DIALOG_LOAD_FILE);
			Log.d(TAG, path.getAbsolutePath());
		}
	});
}
private void loadFileList() {
	try {
		path.mkdirs();
	} catch (SecurityException e) {
		Log.e(TAG, "unable to write on the sd card ");
	}

	// Checks whether path exists
	if (path.exists()) {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				File sel = new File(dir, filename);
				// Filters based on whether the file is hidden or not
				return (sel.isFile() || sel.isDirectory())
						&& !sel.isHidden();

			}
		};

		String[] fList = path.list(filter);
		fileList = new Item[fList.length];
		for (int i = 0; i < fList.length; i++) {
			fileList[i] = new Item(fList[i], R.drawable.file);

			// Convert into file path
			File sel = new File(path, fList[i]);

			// Set drawables
			if (sel.isDirectory()) {
				fileList[i].icon = R.drawable.folder;
				Log.d("DIRECTORY", fileList[i].file);
			} else {
				Log.d("FILE", fileList[i].file);
			}
		}

		if (!firstLvl) {
			Item temp[] = new Item[fileList.length + 1];
			for (int i = 0; i < fileList.length; i++) {
				temp[i + 1] = fileList[i];
			}
			temp[0] = new Item("Up", R.drawable.upload);
			fileList = temp;
		}
	} else {
		Log.e(TAG, "path does not exist");
	}

	adapter = new ArrayAdapter<Item>(this,
			android.R.layout.select_dialog_item, android.R.id.text1,
			fileList) {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// creates view
			View view = super.getView(position, convertView, parent);
			TextView textView = (TextView) view
					.findViewById(android.R.id.text1);

			// put the image on the text view
			textView.setCompoundDrawablesWithIntrinsicBounds(
					fileList[position].icon, 0, 0, 0);

			// add margin between image and text (support various screen
			// densities)
			int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
			textView.setCompoundDrawablePadding(dp5);

			return view;
		}
	};

}

private class Item {
	public String file;
	public int icon;

	public Item(String file, Integer icon) {
		this.file = file;
		this.icon = icon;
	}

	@Override
	public String toString() {
		return file;
	}
}

@Override
protected Dialog onCreateDialog(int id) {
	Dialog dialog = null;
	AlertDialog.Builder builder = new Builder(this);

	if (fileList == null) {
		Log.e(TAG, "No files loaded");
		dialog = builder.create();
		return dialog;
	}

	switch (id) {
	case DIALOG_LOAD_FILE:
		builder.setTitle("Choose your file");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				chosenFile = fileList[which].file;
				File sel = new File(path + "/" + chosenFile);
				if (sel.isDirectory()) {
					firstLvl = false;

					// Adds chosen directory to list
					str.add(chosenFile);
					fileList = null;
					path = new File(sel + "");

					loadFileList();

					removeDialog(DIALOG_LOAD_FILE);
					showDialog(DIALOG_LOAD_FILE);
					Log.d(TAG, path.getAbsolutePath());

				}

				// Checks if 'up' was clicked
				else if (chosenFile.equalsIgnoreCase("up") && !sel.exists()) {

					// present directory removed from list
					String s = str.remove(str.size() - 1);

					// path modified to exclude present directory
					path = new File(path.toString().substring(0,
							path.toString().lastIndexOf(s)));
					fileList = null;

					// if there are no more directories in the list, then
					// its the first level
					if (str.isEmpty()) {
						firstLvl = true;
					}
					loadFileList();

					removeDialog(DIALOG_LOAD_FILE);
					showDialog(DIALOG_LOAD_FILE);
					Log.d(TAG, path.getAbsolutePath());
				}
				// File picked
				else {
					Toast.makeText(Mainactivity.this,chosenFile,Toast.LENGTH_SHORT).show();
					filename.setText(chosenFile);
				}

			}
		});
		break;
	}
	dialog = builder.show();
	return dialog;
}
}
