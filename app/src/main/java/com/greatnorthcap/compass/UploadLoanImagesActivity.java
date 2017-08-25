package com.greatnorthcap.compass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by aspiree15 on 02/08/17.
 */

public class UploadLoanImagesActivity extends Activity {
    private UserSharedPref UserPref = new UserSharedPref();
    ImageButton imageBNinetyDayBankStatement, imageBFirstPayStub, imageBSecondPayStub, imageBThirdPayStub, imageBGovID, imageBEmploymentLetter, imageBAddressProof, imageBPreAuthorizedDebit, imageBSIN, imageBAnotherID, imageBPreAuthorizedAgreement;
    Boolean BankStatementURLCheck, FirstPayStubURLCheck, SecondPayStubURLCheck, ThirdPayStubURLCheck, DriversIDURLCheck, EmploymentLetterURLCheck, ProofOfAddressURLCheck, PreAuthorizedDebitURLCheck, SocialInsuranceNumberURLCheck, OtherIDURLCheck, PreAuthorizedAgreementURLCheck = false;
    Button buttonApplyForLoan;

    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences = getSharedPreferences(UserPref.getSharedPrefName(), Context.MODE_PRIVATE);
        final SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        String loanid = sharedPreferences.getString(UserPref.getSearchedloanidSharedPref(), "Not Available");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadloanimages);
        imageBNinetyDayBankStatement = findViewById(R.id.ninetyDayBankStatementImageButton);
        imageBFirstPayStub = findViewById(R.id.firstRecentPayStubImageButton);
        imageBSecondPayStub = findViewById(R.id.secondRecentPayStubImageButton);
        imageBThirdPayStub = findViewById(R.id.thirdRecentPayStubImageButton);
        imageBGovID = findViewById(R.id.govIDImageButton);
        imageBEmploymentLetter = findViewById(R.id.employmentLetterImageButton);
        imageBAddressProof = findViewById(R.id.addressProofImageButton);
        imageBPreAuthorizedDebit = findViewById(R.id.preauthorizedDebitImageButton);
        imageBSIN = findViewById(R.id.socialInsuranceNumberImageButton);
        imageBAnotherID = findViewById(R.id.anotherIDImageButton);
        imageBPreAuthorizedAgreement = findViewById(R.id.preauthorizedAgreementImageButton);
        buttonApplyForLoan = findViewById(R.id.applyButton);

        String BankStatementURL = UserPref.getServerAddress() + "Images/" + loanid + "/BankStatement.jpg";
        String FirstPayStubURL = UserPref.getServerAddress() + "Images/" + loanid + "/FirstPayStub.jpg";
        String SecondPayStubURL = UserPref.getServerAddress() + "Images/" + loanid + "/SecondPayStub.jpg";
        String ThirdPayStubURL = UserPref.getServerAddress() + "Images/" + loanid + "/ThirdPayStub.jpg";
        String DriversIDURL = UserPref.getServerAddress() + "Images/" + loanid + "/DriversID.jpg";
        String EmploymentLetterURL = UserPref.getServerAddress() + "Images/" + loanid + "/EmploymentLetter.jpg";
        String ProofOfAddressURL = UserPref.getServerAddress() + "Images/" + loanid + "/ProofOfAddress.jpg";
        String PreAuthorizedDebitURL = UserPref.getServerAddress() + "Images/" + loanid + "/PreAuthorizedDebit.jpg";
        String SocialInsuranceNumberURL = UserPref.getServerAddress() + "Images/" + loanid + "/SocialInsuranceNumber.jpg";
        String OtherIDURL = UserPref.getServerAddress() + "Images/" + loanid + "/OtherID.jpg";
        String PreAuthorizedAgreementURL = UserPref.getServerAddress() + "Images/" + loanid + "/PreAuthorizedAgreement.jpg";

        try {
            Picasso.with(this).load(BankStatementURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBNinetyDayBankStatement);
            Picasso.with(this).load(FirstPayStubURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBFirstPayStub);
            Picasso.with(this).load(SecondPayStubURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBSecondPayStub);
            Picasso.with(this).load(ThirdPayStubURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBThirdPayStub);
            Picasso.with(this).load(DriversIDURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBGovID);
            Picasso.with(this).load(EmploymentLetterURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBEmploymentLetter);
            Picasso.with(this).load(ProofOfAddressURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBAddressProof);
            Picasso.with(this).load(PreAuthorizedDebitURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBPreAuthorizedDebit);
            Picasso.with(this).load(SocialInsuranceNumberURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBSIN);
            Picasso.with(this).load(OtherIDURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBAnotherID);
            Picasso.with(this).load(PreAuthorizedAgreementURL).networkPolicy(NetworkPolicy.NO_CACHE).into(imageBPreAuthorizedAgreement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        BankStatementURLCheck = Patterns.WEB_URL.matcher(BankStatementURL).matches();
        FirstPayStubURLCheck = Patterns.WEB_URL.matcher(FirstPayStubURL).matches();
        SecondPayStubURLCheck = Patterns.WEB_URL.matcher(SecondPayStubURL).matches();
        ThirdPayStubURLCheck = Patterns.WEB_URL.matcher(ThirdPayStubURL).matches();
        DriversIDURLCheck = Patterns.WEB_URL.matcher(DriversIDURL).matches();
        EmploymentLetterURLCheck = Patterns.WEB_URL.matcher(EmploymentLetterURL).matches();
        ProofOfAddressURLCheck = Patterns.WEB_URL.matcher(ProofOfAddressURL).matches();
        PreAuthorizedDebitURLCheck = Patterns.WEB_URL.matcher(PreAuthorizedDebitURL).matches();
        SocialInsuranceNumberURLCheck = Patterns.WEB_URL.matcher(SocialInsuranceNumberURL).matches();
        OtherIDURLCheck = Patterns.WEB_URL.matcher(OtherIDURL).matches();
        PreAuthorizedAgreementURLCheck = Patterns.WEB_URL.matcher(PreAuthorizedAgreementURL).matches();
        */

        //BankStatementURLCheck = CheckURL(BankStatementURL);


        imageBNinetyDayBankStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "BankStatement";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBFirstPayStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "FirstPayStub";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBSecondPayStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "SecondPayStub";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBThirdPayStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "ThirdPayStub";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBGovID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "DriversID";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBEmploymentLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "EmploymentLetter";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "ProofOfAddress";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBPreAuthorizedDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "PreAuthorizedDebit";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBSIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "SocialInsuranceNumber";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBAnotherID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "OtherID";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        imageBPreAuthorizedAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UploadType = "PreAuthorizedAgreement";
                prefEditor.putString(UserPref.getUploadtypeSharedPref(), UploadType);
                prefEditor.commit();
                startActivity(new Intent(UploadLoanImagesActivity.this, UploadImageActivity.class));
            }
        });

        buttonApplyForLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BankStatementURLCheck) {
                /*if (!BankStatementURLCheck || !FirstPayStubURLCheck || !SecondPayStubURLCheck || !ThirdPayStubURLCheck || !DriversIDURLCheck
                        || !EmploymentLetterURLCheck || !ProofOfAddressURLCheck || !PreAuthorizedDebitURLCheck || !SocialInsuranceNumberURLCheck
                        || !OtherIDURLCheck || !PreAuthorizedAgreementURLCheck) {*/
                    Toast.makeText(UploadLoanImagesActivity.this, "Please upload all of the required relevant documents.", Toast.LENGTH_LONG).show();
                } else {
                    //This toast needs to be changed.
                    Toast.makeText(UploadLoanImagesActivity.this, "It Works.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
    private Boolean CheckURL(String url) {
        final ScheduledThreadPoolExecutor myTimer = new ScheduledThreadPoolExecutor(1);
        myTimer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Boolean result;
                try {
                    HttpURLConnection.setFollowRedirects(false);
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("HEAD");
                    result = (conn.getResponseCode() == HttpURLConnection.HTTP_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    result = false;
                }
            }
        }, 0,10000, TimeUnit.MILLISECONDS);
        return result;
    }
    */

}
