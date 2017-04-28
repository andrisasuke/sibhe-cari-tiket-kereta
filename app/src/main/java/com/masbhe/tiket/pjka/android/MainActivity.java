package com.masbhe.tiket.pjka.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import com.masbhe.tiket.pjka.android.helper.DroplistAdapter;
import com.masbhe.tiket.pjka.android.helper.DroplistItem;

public class MainActivity extends AppCompatActivity {
    private EditText _txtAlamat;
    private EditText _txtEmail;
    private EditText _txtName;
    private EditText _txtNoKtp;
    private EditText _txtNoTelp;
    private Spinner destinList;
    private Locale id;
    private Spinner keretaList;
    private Spinner originList;
    private SimpleDateFormat sdf;
    private Spinner tglList;
    private RadioGroup radioServer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.id = new Locale("in", "ID");
        this.sdf = new SimpleDateFormat("EEEE, dd MMM yyyy", this.id);

        initStasiunDroplist();
        checkSharedPref();
    }

    public void searchClick(View view) {
        saveSharedPref();
        if (validate()) {
            Intent intent;
            if (VERSION.SDK_INT >= 19) {
                intent = new Intent(this, SearchChromeActivity.class);
            } else {
                intent = new Intent(this, SearchTicket.class);
            }
            intent.putExtra("origin", ((DroplistItem) this.originList.getSelectedItem()).getKey());
            intent.putExtra("destination", ((DroplistItem) this.destinList.getSelectedItem()).getKey());
            intent.putExtra("tgl", ((DroplistItem) this.tglList.getSelectedItem()).getKey());
            intent.putExtra("kereta", ((DroplistItem) this.keretaList.getSelectedItem()).getKey());
            intent.putExtra("nama", this._txtName.getText().toString());
            intent.putExtra("noktp", this._txtNoKtp.getText().toString());
            intent.putExtra("notelp", this._txtNoTelp.getText().toString());
            intent.putExtra("email", this._txtEmail.getText().toString());
            intent.putExtra("alamat", this._txtAlamat.getText().toString());

            int selectedId = radioServer.getCheckedRadioButtonId();
            RadioButton radioButtonServer = (RadioButton) findViewById(selectedId);
            if(radioButtonServer != null)
            intent.putExtra("server", radioButtonServer.getText());
            startActivity(intent);
        }
    }

    public void checkSharedPref() {
        SharedPreferences sp = getSharedPreferences("CariTiketPJKASharedPref", 0);
        this.originList.setSelection(sp.getInt("stAsal", 0));
        this.destinList.setSelection(sp.getInt("stTujuan", 0));
        this.tglList.setSelection(sp.getInt("tglBrkt", 0));
        this.keretaList.setSelection(sp.getInt("kereta", 0));
        this._txtName.setText(sp.getString("name", BuildConfig.FLAVOR));
        this._txtNoKtp.setText(sp.getString("ktp", BuildConfig.FLAVOR));
        this._txtNoTelp.setText(sp.getString("telp", BuildConfig.FLAVOR));
        this._txtEmail.setText(sp.getString("email", BuildConfig.FLAVOR));
        this._txtAlamat.setText(sp.getString("alamat", BuildConfig.FLAVOR));
    }

    public void saveSharedPref() {
        Editor editor = getSharedPreferences("CariTiketPJKASharedPref", 0).edit();
        editor.putInt("stAsal", this.originList.getSelectedItemPosition());
        editor.putInt("stTujuan", this.destinList.getSelectedItemPosition());
        editor.putInt("tglBrkt", this.tglList.getSelectedItemPosition());
        editor.putInt("kereta", this.keretaList.getSelectedItemPosition());
        editor.putString("name", this._txtName.getText().toString());
        editor.putString("ktp", this._txtNoKtp.getText().toString());
        editor.putString("telp", this._txtNoTelp.getText().toString());
        editor.putString("email", this._txtEmail.getText().toString());
        editor.putString("alamat", this._txtAlamat.getText().toString());
        editor.apply();
    }

    private void validateSpinner(Spinner spn, boolean status) {
        TextView tv = (TextView) spn.getSelectedView();
        if (status) {
            tv.setError(null);
        } else {
            tv.setError("Field ini harus diisi");
        }
    }

    public boolean validate() {
        boolean valid = true;
        String asal = ((DroplistItem) this.originList.getSelectedItem()).getKey();
        String tujuan = ((DroplistItem) this.destinList.getSelectedItem()).getKey();
        String tgl = ((DroplistItem) this.tglList.getSelectedItem()).getKey();
        String kereta = ((DroplistItem) this.keretaList.getSelectedItem()).getKey();
        String nama = this._txtName.getText().toString();
        String noKtp = this._txtNoKtp.getText().toString();
        String telp = this._txtNoTelp.getText().toString();
        String email = this._txtEmail.getText().toString();
        String alamat = this._txtAlamat.getText().toString();
        if (asal == null || asal.equals(BuildConfig.FLAVOR)) {
            validateSpinner(this.originList, false);
            valid = false;
        } else {
            validateSpinner(this.originList, true);
        }
        if (tujuan == null || tujuan.equals(BuildConfig.FLAVOR)) {
            validateSpinner(this.destinList, false);
            valid = false;
        } else {
            validateSpinner(this.destinList, true);
        }
        if (tgl == null || tgl.equals(BuildConfig.FLAVOR)) {
            validateSpinner(this.tglList, false);
            valid = false;
        } else {
            validateSpinner(this.tglList, true);
        }
        if (kereta == null || kereta.equals(BuildConfig.FLAVOR)) {
            validateSpinner(this.keretaList, false);
            valid = false;
        } else {
            validateSpinner(this.keretaList, true);
        }
        if (nama == null || nama.equals(BuildConfig.FLAVOR)) {
            this._txtName.setError("Nama Tidak Boleh Kosong");
            valid = false;
        } else {
            this._txtName.setError(null);
        }
        if (noKtp == null || noKtp.equals(BuildConfig.FLAVOR)) {
            this._txtNoKtp.setError("No KTP Tidak boleh kosong");
            valid = false;
        } else {
            this._txtNoKtp.setError(null);
        }
        if (telp == null || telp.equals(BuildConfig.FLAVOR)) {
            this._txtNoTelp.setError("No.Telp Tidak boleh kosong");
            valid = false;
        } else {
            this._txtNoTelp.setError(null);
        }
        if (email == null || email.equals(BuildConfig.FLAVOR) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this._txtEmail.setError("Email Tidak Valid");
            valid = false;
        } else {
            this._txtEmail.setError(null);
        }
        if (alamat == null || alamat.equals(BuildConfig.FLAVOR)) {
            this._txtAlamat.setError("Alamat tidak boleh kosong");
            return false;
        }
        this._txtAlamat.setError(null);
        return valid;
    }

    private void initStasiunDroplist() {
        this.originList = (Spinner) findViewById(R.id.originSpinner);
        this.originList.setAdapter(new DroplistAdapter(this, buildStasiunList("Stasiun Asal")));
        this.destinList = (Spinner) findViewById(R.id.destinSpinner);
        this.destinList.setAdapter(new DroplistAdapter(this, buildStasiunList("Stasiun Tujuan")));
        this.tglList = (Spinner) findViewById(R.id.tglSpinner);
        this.tglList.setAdapter(new DroplistAdapter(this, buildTgl()));
        this.keretaList = (Spinner) findViewById(R.id.keretaSpinner);
        this.keretaList.setAdapter(new DroplistAdapter(this, buildKeretaList()));
        this._txtAlamat = (EditText) findViewById(R.id.txtAlamat);
        this._txtEmail = (EditText) findViewById(R.id.txtEmail);
        this._txtName = (EditText) findViewById(R.id.txtNama);
        this._txtNoKtp = (EditText) findViewById(R.id.txtNoKTP);
        this._txtNoTelp = (EditText) findViewById(R.id.txtNoTelp);
        this.radioServer = (RadioGroup) findViewById(R.id.RGServer);
    }

    private ArrayList<DroplistItem> buildTgl() {
        ArrayList<DroplistItem> list = new ArrayList<>();
        list.add(new DroplistItem(BuildConfig.FLAVOR, "     --Tgl Keberangkatan--"));
        Calendar cal = Calendar.getInstance();
        list.add(new DroplistItem("0", this.sdf.format(cal.getTime())));
        for (int i = 0; i < 90; i++) {
            cal.add(Calendar.DATE, 1);
            list.add(new DroplistItem(BuildConfig.FLAVOR + (i + 1), this.sdf.format(cal.getTime())));
        }
        return list;
    }

    private ArrayList<DroplistItem> buildKeretaList() {
        ArrayList<DroplistItem> list = new ArrayList<>();
        list.add(new DroplistItem(BuildConfig.FLAVOR, "     --Kereta--"));
        list.add(new DroplistItem("BOGOWONTO", "BOGOWONTO"));
        list.add(new DroplistItem("KRAKATAU", "KRAKATAU"));
        list.add(new DroplistItem("PROGO", "PROGO"));
        list.add(new DroplistItem("BENGAWAN", "BENGAWAN"));
        list.add(new DroplistItem("JAKA TINGKIR", "JAKA TINGKIR"));
        list.add(new DroplistItem("GAYA BARU MALAM SELATAN", "GAYA BARU MALAM SELATAN"));
        list.add(new DroplistItem("GAJAHWONG", "GAJAHWONG"));
        list.add(new DroplistItem("SENJA UTAMA SOLO", "SENJA UTAMA SOLO"));
        list.add(new DroplistItem("ARGO DWIPANGGA", "ARGO DWIPANGGA"));
        list.add(new DroplistItem("TAKSAKA PAGI", "TAKSAKA PAGI"));
        list.add(new DroplistItem("BIMA", "BIMA"));
        list.add(new DroplistItem("GAJAYANA", "GAJAYANA"));
        list.add(new DroplistItem("ARGO LAWU", "ARGO LAWU"));
        list.add(new DroplistItem("TAKSAKA MALAM", "TAKSAKA MALAM"));
        list.add(new DroplistItem("BRANTAS", "BRANTAS"));
        list.add(new DroplistItem("BRANTAS LEBARAN", "BRANTAS LEBARAN"));
        list.add(new DroplistItem("MATARMAJA", "MATARMAJA"));
        list.add(new DroplistItem("MATARMAJA LEB", "MATARMAJA LEB"));
        list.add(new DroplistItem("MAJAPAHIT", "MAJAPAHIT"));
        list.add(new DroplistItem("TAWANG JAYA", "TAWANG JAYA"));
        list.add(new DroplistItem("TAWANG JAYA LEB", "TAWANG JAYA LEB"));
        list.add(new DroplistItem("JAYABAYA", "JAYABAYA"));
        list.add(new DroplistItem("LODAYA PAGI", "LODAYA PAGI"));
        list.add(new DroplistItem("ARGO WILIS", "ARGO WILIS"));
        list.add(new DroplistItem("MALABAR ", "MALABAR "));
        list.add(new DroplistItem("MUTIARA SELATAN", "MUTIARA SELATAN"));
        list.add(new DroplistItem("LODAYA MALAM ", "LODAYA MALAM "));
        list.add(new DroplistItem("TURANGGA", "TURANGGA"));
        list.add(new DroplistItem("ARGO SINDORO LEBARAN", "ARGO SINDORO LEBARAN"));
        list.add(new DroplistItem("ARGO MURIA LEBARAN", "ARGO MURIA LEBARAN"));
        list.add(new DroplistItem("ARGO MURIA TAMBAHAN", "ARGO MURIA TAMBAHAN"));
        list.add(new DroplistItem("SEMBRANI LEBARAN", "SEMBRANI LEBARAN"));

        list.add(new DroplistItem("SENJA UTAMA YK" , "SENJA UTAMA YK"));
        list.add(new DroplistItem("MENOREH", "MENOREH"));
        list.add(new DroplistItem("MENOREH TAMBAHAN", "MENOREH TAMBAHAN"));
        list.add(new DroplistItem("MENOREH LEBARAN", "MENOREH LEBARAN"));
        list.add(new DroplistItem("BANGUNKARTA", "BANGUNKARTA"));
        list.add(new DroplistItem("ARGO MURIA", "ARGO MURIA"));
        list.add(new DroplistItem("ARGO ANGGREK PAGI", "ARGO ANGGREK PAGI"));
        list.add(new DroplistItem("ARGO SINDORO", "ARGO SINDORO"));
        list.add(new DroplistItem("ARGO SINDORO TAMBAHAN", "ARGO SINDORO TAMBAHAN"));
        list.add(new DroplistItem("SEMBRANI", "SEMBRANI"));
        list.add(new DroplistItem("ARGO ANGGREK MALAM", "ARGO ANGGREK MALAM"));
        list.add(new DroplistItem("KERTAJAYA", "KERTAJAYA"));
        list.add(new DroplistItem("KERTAJAYA LEBARAN", "KERTAJAYA LEBARAN"));
        list.add(new DroplistItem("SAWUNGGALIH MALAM", "SAWUNGGALIH MALAM"));
        list.add(new DroplistItem("SAWUNGGALIH", "SAWUNGGALIH"));
        list.add(new DroplistItem("KUTOJAYA UTARA", "KUTOJAYA UTARA"));
        list.add(new DroplistItem("KUTOJAYA UTARA LEBARAN", "KUTOJAYA UTARA LEBARAN"));
        list.add(new DroplistItem("KUTOJAYA UTARA TAMBAHAN", "KUTOJAYA UTARA TAMBAHAN"));
        list.add(new DroplistItem("PURWOJAYA LEBARAN", "PURWOJAYA LEBARAN"));
        list.add(new DroplistItem("PURWOJAYA", "PURWOJAYA"));
        list.add(new DroplistItem("SERAYU", "SERAYU"));
        list.add(new DroplistItem("ARGO PARAHYANGAN", "ARGO PARAHYANGAN"));
     
        list.add(new DroplistItem("MANTAB LEBARAN", "MANTAB LEBARAN"));
        list.add(new DroplistItem("TAWANG JAYA LEBARAN", "TAWANG JAYA LEBARAN"));
        
        list.add(new DroplistItem("KLB TAMBAHAN GMR-SGU", "KLB TAMBAHAN GMR-SGU"));
        list.add(new DroplistItem("KLB TAMBAHAN SGU-GMR", "KLB TAMBAHAN SGU-GMR"));
        list.add(new DroplistItem("KLB TAMBAHAN PSE-PWS", "KLB TAMBAHAN PSE-PWS"));
        list.add(new DroplistItem("KLB TAMBAHAN PWS-PSE", "KLB TAMBAHAN PWS-PSE"));
        list.add(new DroplistItem("TAKSAKA", "TAKSAKA"));
        list.add(new DroplistItem("TAKSAKA LEBARAN", "TAKSAKA LEBARAN"));
        list.add(new DroplistItem("TAKSAKA TAMBAHAN", "TAKSAKA TAMBAHAN"));
        list.add(new DroplistItem("ARGO DWIPANGGA FAKULTATIF","ARGO DWIPANGGA FAKULTATIF"));
        list.add(new DroplistItem("ARGO LAWU FAKULTATIF", "ARGO LAWU FAKULTATIF"));
        list.add(new DroplistItem("GAJAYANA LEBARAN", "GAJAYANA LEBARAN"));
        list.add(new DroplistItem("TAKSAKA MALAM LEBARAN", "TAKSAKA MALAM LEBARAN"));
        list.add(new DroplistItem("GUMARANG", "GUMARANG"));
        list.add(new DroplistItem("FAJAR UTAMA YK", "FAJAR UTAMA YK"));

        Collections.sort(list, new Comparator<DroplistItem>() {
            @Override
            public int compare(final DroplistItem object1, final DroplistItem object2) {
                return object1.getLabel().compareTo(object2.getLabel());
            }
        });
        return list;
    }

    private ArrayList<DroplistItem> buildStasiunList(String emptyLabel) {
        ArrayList<DroplistItem> list = new ArrayList<>();
        list.add(new DroplistItem(BuildConfig.FLAVOR, "     --" + emptyLabel + "--"));
        list.add(new DroplistItem("BD#BANDUNG", "BANDUNG"));
        list.add(new DroplistItem("CCL#CICALENGKA", "CICALENGKA"));
        list.add(new DroplistItem("CD#CIKADONGDONG", "CIKADONGDONG"));
        list.add(new DroplistItem("CMI#CIMAHI", "CIMAHI"));
        list.add(new DroplistItem("GDB#GEDEBAGE", "GEDEBAGE"));
        list.add(new DroplistItem("HRP#HAURPUGUR", "HAURPUGUR"));
        list.add(new DroplistItem("KAC#KIARACONDONG", "KIARACONDONG"));
        list.add(new DroplistItem("PDL#PADALARANG", "PADALARANG"));
        list.add(new DroplistItem("RCK#RANCAEKEK", "RANCAEKEK"));
        list.add(new DroplistItem("RH#RENDEH", "RENDEH"));
        list.add(new DroplistItem("BJR#BANJAR", "BANJAR"));
        list.add(new DroplistItem("LN#LANGEN", "LANGEN"));
        list.add(new DroplistItem("KRR#KARANGSARI", "KARANGSARI"));
        list.add(new DroplistItem("KBS#KEBASEN", "KEBASEN"));
        list.add(new DroplistItem("KJ#KEMRANJEN", "KEMRANJEN"));
        list.add(new DroplistItem("NTG#NOTOG", "NOTOG"));
        list.add(new DroplistItem("SPH#SUMPIUH", "SUMPIUH"));
        list.add(new DroplistItem("TBK#TAMBAK", "TAMBAK"));
        list.add(new DroplistItem("BW#BANYUWANGIBARU", "BANYUWANGIBARU"));
        list.add(new DroplistItem("GLM#GLENMORE", "GLENMORE"));
        list.add(new DroplistItem("KBR#KALIBARU", "KALIBARU"));
        list.add(new DroplistItem("KSL#KALISETAIL", "KALISETAIL"));
        list.add(new DroplistItem("KNE#KARANGASEM", "KARANGASEM"));
        list.add(new DroplistItem("RGP#ROGOJAMPI", "ROGOJAMPI"));
        list.add(new DroplistItem("SWD#SUMBERWADUNG", "SUMBERWADUNG"));
        list.add(new DroplistItem("TGR#TEMUGURUH", "TEMUGURUH"));
        list.add(new DroplistItem("BTG#BATANG", "BATANG"));
        list.add(new DroplistItem("UJN#UJUNGNEGORO", "UJUNGNEGORO"));
        list.add(new DroplistItem("BTA#BATURAJA", "BATURAJA"));
        list.add(new DroplistItem("PNW#PANINJAWAN", "PANINJAWAN"));
        list.add(new DroplistItem("BKS#BEKASI", "BEKASI"));
        list.add(new DroplistItem("BIJ#BINJAI", "BINJAI"));
        list.add(new DroplistItem("BL#BLITAR", "BLITAR"));
        list.add(new DroplistItem("GRM#GARUM", "GARUM"));
        list.add(new DroplistItem("KSB#KESAMBEN", "KESAMBEN"));
        list.add(new DroplistItem("TAL#TALUN", "TALUN"));
        list.add(new DroplistItem("WG#WLINGI", "WLINGI"));
        list.add(new DroplistItem("CU#CEPU", "CEPU"));
        list.add(new DroplistItem("DPL#DOPLANG", "DOPLANG"));
        list.add(new DroplistItem("RBG#RANDUBLATUNG", "RANDUBLATUNG"));
        list.add(new DroplistItem("BTT#BATU TULIS", "BATU TULIS"));
        list.add(new DroplistItem("BOO#BOGOR", "BOGOR"));
        list.add(new DroplistItem("CGB#CIGOMBONG", "CIGOMBONG"));
        list.add(new DroplistItem("MSG#MASENG", "MASENG"));
        list.add(new DroplistItem("PRP#PARUNGPANJANG", "PARUNGPANJANG"));
        list.add(new DroplistItem("BJ#BOJONEGORO", "BOJONEGORO"));
        list.add(new DroplistItem("KIT#KALITIDU", "KALITIDU"));
        list.add(new DroplistItem("SRJ#SUMBERREJO", "SUMBERREJO"));
        list.add(new DroplistItem("TW#TELAWA", "TELAWA"));
        list.add(new DroplistItem("BB#BREBES", "BREBES"));
        list.add(new DroplistItem("BKA#BULAKAMBA", "BULAKAMBA"));
        list.add(new DroplistItem("BMA#BUMIAYU", "BUMIAYU"));
        list.add(new DroplistItem("KGG#KETANGGUNGAN", "KETANGGUNGAN"));
        list.add(new DroplistItem("KGB#KETANGGUNGAN BARAT", "KETANGGUNGAN BARAT"));
        list.add(new DroplistItem("KRT#KRETEK", "KRETEK"));
        list.add(new DroplistItem("LRA#LARANGAN", "LARANGAN"));
        list.add(new DroplistItem("LR#LARANGAN", "LARANGAN"));
        list.add(new DroplistItem("LG#LINGGAPURA", "LINGGAPURA"));
        list.add(new DroplistItem("PAT#PATUGURAN", "PATUGURAN"));
        list.add(new DroplistItem("SGG#SONGGOM", "SONGGOM"));
        list.add(new DroplistItem("TGN#TANJUNG", "TANJUNG"));
        list.add(new DroplistItem("BJI#BANJARSARI", "BANJARSARI"));
        list.add(new DroplistItem("CI#CIAMIS", "CIAMIS"));
        list.add(new DroplistItem("CJ#CIANJUR", "CIANJUR"));
        list.add(new DroplistItem("CBB#CIBEBER", "CIBEBER"));
        list.add(new DroplistItem("CLK#CILAKU", "CILAKU"));
        list.add(new DroplistItem("LP#LAMPEGAN", "LAMPEGAN"));
        list.add(new DroplistItem("CKP#CIKAMPEK", "CIKAMPEK"));
        list.add(new DroplistItem("CP#CILACAP", "CILACAP"));
        list.add(new DroplistItem("CPI#CIPARI", "CIPARI"));
        list.add(new DroplistItem("GDM#GANDRUNGMANGUN", "GANDRUNGMANGUN"));
        list.add(new DroplistItem("GM#GUMILIR", "GUMILIR"));
        list.add(new DroplistItem("JRL#JERUKLEGI", "JERUKLEGI"));
        list.add(new DroplistItem("KWG#KAWUNGANTEN", "KAWUNGANTEN"));
        list.add(new DroplistItem("KYA#KROYA", "KROYA"));
        list.add(new DroplistItem("LBG#LEBENG", "LEBENG"));
        list.add(new DroplistItem("MA#MAOS", "MAOS"));
        list.add(new DroplistItem("MLW#MELUWUNG", "MELUWUNG"));
        list.add(new DroplistItem("SDR#SIDAREJA", "SIDAREJA"));
        list.add(new DroplistItem("SKP#SIKAMPUH", "SIKAMPUH"));
        list.add(new DroplistItem("CLG#CILEGON", "CILEGON"));
        list.add(new DroplistItem("MER#MERAK", "MERAK"));
        list.add(new DroplistItem("AWN#ARJAWINANGUN", "ARJAWINANGUN"));
        list.add(new DroplistItem("BBK#BABAKAN", "BABAKAN"));
        list.add(new DroplistItem("CLD#CILEDUG", "CILEDUG"));
        list.add(new DroplistItem("CN#CIREBON", "CIREBON"));
        list.add(new DroplistItem("CNP#CIREBONPRUJAKAN", "CIREBONPRUJAKAN"));
        list.add(new DroplistItem("KRW#KARANGSUWUNG", "KARANGSUWUNG"));
        list.add(new DroplistItem("LOS#LOSARI", "LOSARI"));
        list.add(new DroplistItem("LWG#LUWUNG", "LUWUNG"));
        list.add(new DroplistItem("SDU#SINDANGLAUT", "SINDANGLAUT"));
        list.add(new DroplistItem("WDW#WARUDUWUR", "WARUDUWUR"));
        list.add(new DroplistItem("LBP#LUBUKPAKAM", "LUBUKPAKAM"));
        list.add(new DroplistItem("BBG#BRUMBUNG", "BRUMBUNG"));
        list.add(new DroplistItem("DEN#DENPASAR", "DENPASAR"));
        list.add(new DroplistItem("CB#CIBATU", "CIBATU"));
        list.add(new DroplistItem("CPD#CIPEUNDEUY", "CIPEUNDEUY"));
        list.add(new DroplistItem("LBJ#LEBAKJERO", "LEBAKJERO"));
        list.add(new DroplistItem("LL#LELES", "LELES"));
        list.add(new DroplistItem("NG#NAGREG", "NAGREG"));
        list.add(new DroplistItem("WB#WARUNG BANDREK", "WARUNG BANDREK"));
        list.add(new DroplistItem("GB#GOMBONG", "GOMBONG"));
        list.add(new DroplistItem("IJ#IJO", "IJO"));
        list.add(new DroplistItem("GD#GUNDIH", "GUNDIH"));
        list.add(new DroplistItem("KGT#KARANGJATI", "KARANGJATI"));
        list.add(new DroplistItem("KEJ#KEDUNGJATI", "KEDUNGJATI"));
        list.add(new DroplistItem("NBO#NGROMBO", "NGROMBO"));
        list.add(new DroplistItem("CLH#CILEGEH", "CILEGEH"));
        list.add(new DroplistItem("HGL#HAURGEULIS", "HAURGEULIS"));
        list.add(new DroplistItem("JTB#JATIBARANG", "JATIBARANG"));
        list.add(new DroplistItem("KAB#KADOKANGANGABUS", "KADOKANGANGABUS"));
        list.add(new DroplistItem("KTM#KERTASEMAYA", "KERTASEMAYA"));
        list.add(new DroplistItem("TIS#TERISI", "TERISI"));
        list.add(new DroplistItem("GMR#GAMBIR", "GAMBIR"));
        list.add(new DroplistItem("JAKK#JAKARTA KOTA", "JAKARTA KOTA"));
        list.add(new DroplistItem("JNG#JATINEGARA", "JATINEGARA"));
        list.add(new DroplistItem("MRI#MANGGARAI", "MANGGARAI"));
        list.add(new DroplistItem("PSE#PASAR SENEN", "PASAR SENEN"));
        list.add(new DroplistItem("THB#TANAH ABANG", "TANAH ABANG"));
        list.add(new DroplistItem("TPK#TANJUNG PRIUK", "TANJUNG PRIUK"));
        list.add(new DroplistItem("BDW#BANGODUWA", "BANGODUWA"));
        list.add(new DroplistItem("JTR#JATIROTO", "JATIROTO"));
        list.add(new DroplistItem("JR#JEMBER", "JEMBER"));
        list.add(new DroplistItem("KLT#KALISAT", "KALISAT"));
        list.add(new DroplistItem("RBP#RAMBIPUJI", "RAMBIPUJI"));
        list.add(new DroplistItem("TGL#TANGGUL", "TANGGUL"));
        list.add(new DroplistItem("JG#JOMBANG", "JOMBANG"));
        list.add(new DroplistItem("SMB#SEMBUNG", "SEMBUNG"));
        list.add(new DroplistItem("KA#KARANGANYAR", "KARANGANYAR"));
        list.add(new DroplistItem("KM#KEBUMEN", "KEBUMEN"));
        list.add(new DroplistItem("KWN#KUTOWINANGUN", "KUTOWINANGUN"));
        list.add(new DroplistItem("PRB#PREMBUN", "PREMBUN"));
        list.add(new DroplistItem("SRW#SRUWENG", "SRUWENG"));
        list.add(new DroplistItem("WNS#WONOSARI", "WONOSARI"));
        list.add(new DroplistItem("KD#KEDIRI", "KEDIRI"));
        list.add(new DroplistItem("PPR#PAPAR", "PAPAR"));
        list.add(new DroplistItem("KBD#KALIBODRI", "KALIBODRI"));
        list.add(new DroplistItem("KLN#KALIWUNGU", "KALIWUNGU"));
        list.add(new DroplistItem("WLR#WELERI", "WELERI"));
        list.add(new DroplistItem("KIS#KISARAN", "KISARAN"));
        list.add(new DroplistItem("KT#KLATEN", "KLATEN"));
        list.add(new DroplistItem("SWT#SROWOT", "SROWOT"));
        list.add(new DroplistItem("MP#MARTAPURA", "MARTAPURA"));
        list.add(new DroplistItem("KTA#KUTOARJO", "KUTOARJO"));
        list.add(new DroplistItem("BGM#BUNGAMAS", "BUNGAMAS"));
        list.add(new DroplistItem("LT#LAHAT", "LAHAT"));
        list.add(new DroplistItem("SNA#SAUNGNAGA", "SAUNGNAGA"));
        list.add(new DroplistItem("TI#TEBINGTINGGI", "TEBINGTINGGI"));
        list.add(new DroplistItem("BBT#BABAT", "BABAT"));
        list.add(new DroplistItem("LMG#LAMONGAN", "LAMONGAN"));
        list.add(new DroplistItem("BKI#BEKRI", "BEKRI"));
        list.add(new DroplistItem("BBU#BLAMBANGANUMPU", "BLAMBANGANUMPU"));
        list.add(new DroplistItem("GHM#GIHAM", "GIHAM"));
        list.add(new DroplistItem("KB#KOTABUMI", "KOTABUMI"));
        list.add(new DroplistItem("TNK#TANJUNGKARANG", "TANJUNGKARANG"));
        list.add(new DroplistItem("TLY#TULUNGBUYUT", "TULUNGBUYUT"));
        list.add(new DroplistItem("RK#RANGKAS BITUNG", "RANGKAS BITUNG"));
        list.add(new DroplistItem("KOP#KOTAPADANG", "KOTAPADANG"));
        list.add(new DroplistItem("LLG#LUBUK LINGGAU", "LUBUK LINGGAU"));
        list.add(new DroplistItem("MSL#MUARASALING", "MUARASALING"));
        list.add(new DroplistItem("KK#KLAKAH", "KLAKAH"));
        list.add(new DroplistItem("CRB#CARUBAN", "CARUBAN"));
        list.add(new DroplistItem("MN#MADIUN", "MADIUN"));
        list.add(new DroplistItem("PA#PARON", "PARON"));
        list.add(new DroplistItem("SRD#SARADAN", "SARADAN"));
        list.add(new DroplistItem("BAT#BARAT", "BARAT"));
        list.add(new DroplistItem("BMG#BLIMBING", "BLIMBING"));
        list.add(new DroplistItem("KPN#KEPANJEN", "KEPANJEN"));
        list.add(new DroplistItem("LW#LAWANG", "LAWANG"));
        list.add(new DroplistItem("ML#MALANG", "MALANG"));
        list.add(new DroplistItem("MLK#MALANG KOTA LAMA", "MALANG KOTA LAMA"));
        list.add(new DroplistItem("SBP#SUMBERPUCUNG", "SUMBERPUCUNG"));
        list.add(new DroplistItem("AKB#AEKLOBA", "AEKLOBA"));
        list.add(new DroplistItem("BJL#BAJALINGGEI", "BAJALINGGEI"));
        list.add(new DroplistItem("BAP#BANDARKALIPAH", "BANDARKALIPAH"));
        list.add(new DroplistItem("BDT#BANDARTINGGI", "BANDARTINGGI"));
        list.add(new DroplistItem("BTK#BATANGKUIS", "BATANGKUIS"));
        list.add(new DroplistItem("DMR#DOLOKMERANGIR", "DOLOKMERANGIR"));
        list.add(new DroplistItem("HL#HENGELO", "HENGELO"));
        list.add(new DroplistItem("LMP#LIMAPULUH", "LIMAPULUH"));
        list.add(new DroplistItem("MDN#MEDAN", "MEDAN"));
        list.add(new DroplistItem("MBM#MEMBANGMUDA", "MEMBANGMUDA"));
        list.add(new DroplistItem("PHA#PADANGHALABAN", "PADANGHALABAN"));
        list.add(new DroplistItem("PME#PAMINGKE", "PAMINGKE"));
        list.add(new DroplistItem("PBA#PERBAUNGAN", "PERBAUNGAN"));
        list.add(new DroplistItem("PRA#PERLANAAN", "PERLANAAN"));
        list.add(new DroplistItem("PUR#PULURAJA", "PULURAJA"));
        list.add(new DroplistItem("SBJ#SEI BEJANGKAR", "SEI BEJANGKAR"));
        list.add(new DroplistItem("TBI#TEBING TINGGI", "TEBING TINGGI"));
        list.add(new DroplistItem("MR#MOJOKERTO", "MOJOKERTO"));
        list.add(new DroplistItem("ME#MUARA ENIM", "MUARA ENIM"));
        list.add(new DroplistItem("UJM#UJANMAS", "UJANMAS"));
        list.add(new DroplistItem("BGR#BAGOR", "BAGOR"));
        list.add(new DroplistItem("BRN#BARON", "BARON"));
        list.add(new DroplistItem("KTS#KERTOSONO", "KERTOSONO"));
        list.add(new DroplistItem("NJ#NGANJUK", "NGANJUK"));
        list.add(new DroplistItem("WIL#WILANGAN", "WILANGAN"));
        list.add(new DroplistItem("GG#GENENG", "GENENG"));
        list.add(new DroplistItem("KG#KEDUNGGALAR", "KEDUNGGALAR"));
        list.add(new DroplistItem("WK#WALIKUKUN", "WALIKUKUN"));
        list.add(new DroplistItem("KPT#KERTAPATI", "KERTAPATI"));
        list.add(new DroplistItem("BG#BANGIL", "BANGIL"));
        list.add(new DroplistItem("GI#GRATI", "GRATI"));
        list.add(new DroplistItem("PS#PASURUAN", "PASURUAN"));
        list.add(new DroplistItem("BJG#BOJONG", "BOJONG"));
        list.add(new DroplistItem("KNS#KRENGSENG", "KRENGSENG"));
        list.add(new DroplistItem("PK#PEKALONGAN", "PEKALONGAN"));
        list.add(new DroplistItem("PLB#PLABUAN", "PLABUAN"));
        list.add(new DroplistItem("SRI#SRAGI", "SRAGI"));
        list.add(new DroplistItem("CO#COMAL", "COMAL"));
        list.add(new DroplistItem("PML#PEMALANG", "PEMALANG"));
        list.add(new DroplistItem("PTA#PETARUKAN", "PETARUKAN"));
        list.add(new DroplistItem("SIR#SIANTAR", "SIANTAR"));
        list.add(new DroplistItem("BBD#BABADAN", "BABADAN"));
        list.add(new DroplistItem("BIB#BLIMBINGPENDOPO", "BLIMBINGPENDOPO"));
        list.add(new DroplistItem("GNM#GUNUNGMEGANG", "GUNUNGMEGANG"));
        list.add(new DroplistItem("PBM#PRABUMULIH", "PRABUMULIH"));
        list.add(new DroplistItem("PB#PROBOLINGGO", "PROBOLINGGO"));
        list.add(new DroplistItem("PLD#PLERED", "PLERED"));
        list.add(new DroplistItem("PWK#PURWAKARTA", "PURWAKARTA"));
        list.add(new DroplistItem("PWT#PURWOKERTO", "PURWOKERTO"));
        list.add(new DroplistItem("JN#JENAR", "JENAR"));
        list.add(new DroplistItem("MBU#MERBAU", "MERBAU"));
        list.add(new DroplistItem("RAP#RANTAU PRAPAT", "RANTAU PRAPAT"));
        list.add(new DroplistItem("SMC#SEMARANGPONCOL", "SEMARANGPONCOL"));
        list.add(new DroplistItem("SMT#SEMARANGTAWANG", "SEMARANGTAWANG"));
        list.add(new DroplistItem("SG#SERANG", "SERANG"));
        list.add(new DroplistItem("RPH#RAMPAH", "RAMPAH"));
        list.add(new DroplistItem("GDG#GEDANGAN", "GEDANGAN"));
        list.add(new DroplistItem("KRN#KRIAN", "KRIAN"));
        list.add(new DroplistItem("SPJ#SEPANJANG", "SEPANJANG"));
        list.add(new DroplistItem("SDA#SIDOARJO", "SIDOARJO"));
        list.add(new DroplistItem("WR#WARU", "WARU"));
        list.add(new DroplistItem("PWS#PURWOSARI", "PURWOSARI"));
        list.add(new DroplistItem("SLO#SOLOBALAPAN", "SOLOBALAPAN"));
        list.add(new DroplistItem("SK#SOLOJEBRES", "SOLOJEBRES"));
        list.add(new DroplistItem("KRO#KEBONROMO", "KEBONROMO"));
        list.add(new DroplistItem("KDB#KEDUNGBANTENG", "KEDUNGBANTENG"));
        list.add(new DroplistItem("MSR#MASARAN", "MASARAN"));
        list.add(new DroplistItem("SR#SRAGEN", "SRAGEN"));
        list.add(new DroplistItem("CRA#CIPUNEGARA", "CIPUNEGARA"));
        list.add(new DroplistItem("PGB#PEGADENBARU", "PEGADENBARU"));
        list.add(new DroplistItem("TJS#TANJUNGRASA", "TANJUNGRASA"));
        list.add(new DroplistItem("CBD#CIBADAK", "CIBADAK"));
        list.add(new DroplistItem("CCR#CICURUG", "CICURUG"));
        list.add(new DroplistItem("CRG#CIREUNGAS", "CIREUNGAS"));
        list.add(new DroplistItem("CSA#CISAAT", "CISAAT"));
        list.add(new DroplistItem("GDS#GANDASOLI", "GANDASOLI"));
        list.add(new DroplistItem("KE#KARANG TENGAH", "KARANG TENGAH"));
        list.add(new DroplistItem("PRK#PARUNGKUDA", "PARUNGKUDA"));
        list.add(new DroplistItem("SI#SUKABUMI", "SUKABUMI"));
        list.add(new DroplistItem("SGU#SURABAYA GUBENG", "SURABAYA GUBENG"));
        list.add(new DroplistItem("SBI#SURABAYA PASAR TURI", "SURABAYA PASAR TURI"));
        list.add(new DroplistItem("WO#WONOKROMO", "WONOKROMO"));
        list.add(new DroplistItem("TNG#TANGERANG", "TANGERANG"));
        list.add(new DroplistItem("TNB#TANJUNGBALAI", "TANJUNGBALAI"));
        list.add(new DroplistItem("AW#AWIPARI", "AWIPARI"));
        list.add(new DroplistItem("CAW#CIAWI", "CIAWI"));
        list.add(new DroplistItem("MNJ#MANONJAYA", "MANONJAYA"));
        list.add(new DroplistItem("RJP#RAJAPOLAH", "RAJAPOLAH"));
        list.add(new DroplistItem("TSM#TASIKMALAYA", "TASIKMALAYA"));
        list.add(new DroplistItem("PPK#PRUPUK", "PRUPUK"));
        list.add(new DroplistItem("SLW#SLAWI", "SLAWI"));
        list.add(new DroplistItem("TG#TEGAL", "TEGAL"));
        list.add(new DroplistItem("NT#NGUNUT", "NGUNUT"));
        list.add(new DroplistItem("TA#TULUNGAGUNG", "TULUNGAGUNG"));
        list.add(new DroplistItem("BBN#BRAMBANAN", "BRAMBANAN"));
        list.add(new DroplistItem("LPN#LEMPUYANGAN", "LEMPUYANGAN"));
        list.add(new DroplistItem("STL#SENTOLO", "SENTOLO"));
        list.add(new DroplistItem("WT#WATES", "WATES"));
        list.add(new DroplistItem("YK#YOGYAKARTA", "YOGYAKARTA"));

        Collections.sort(list, new Comparator<DroplistItem>() {
            @Override
            public int compare(final DroplistItem object1, final DroplistItem object2) {
                return object1.getLabel().compareTo(object2.getLabel());
            }
        });

        return list;
    }

}
