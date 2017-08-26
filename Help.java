package com.example.ashwanigupta.moh2go;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4,tv5, tv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        tv6=(TextView)findViewById(R.id.tv6);

        tv1.setText("The Credit Guarantee Fund Scheme for Micro and Small Enterprises");
        tv2.setText("he Credit Guarantee Fund Scheme for Micro and Small Enterprises (CGMSE) was launched by the Government of India to provide collateral-free credit to Indian MSMEs. Both the existing and the new enterprises are eligible for the scheme.");
        tv3.setText(" Technology Upgradation Fund Scheme (TUFS) For Textile Industry");
        tv4.setText("Ministry of Textiles introduced the Technology up gradation fund scheme (TUFS) for textiles and jute industry in April 1999 to facilitate induction of state-of-the- art technology by the textile units.");
        tv5.setText("Credit Linked Capital Subsidy Scheme for Technology Upgradation (CLCSS)");
        tv6.setText("Upgradation of the process as well as the corresponding plant and machinery is important to help SMEs reduce the cost of production and remain price competitive in the global market. To help SMEs flourish in international trade markets, the Ministry of Small Scale Industries (SSI) runs a scheme for technology up gradation of Small Scale Industries. Known as the Credit Linked Capital Subsidy Scheme (CLCSS), it aims at facilitating technology up gradation by providing the upfront capital subsidy of 15% (limited to maximum Rs.15 lakhs) to SSI units for credit availed by them for the modernisation of their plant and machinery.");
    }
}
