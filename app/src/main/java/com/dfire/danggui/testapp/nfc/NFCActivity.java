package com.dfire.danggui.testapp.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.dfire.danggui.testapp.R;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author DangGui
 * @create 2018/4/12.
 */
public class NFCActivity extends AppCompatActivity {
    EditText mEditText;
    private List<Tag> mTags = new ArrayList<>();
    private IntentFilter[] intentFiltersArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    private String[][] techListsArray;
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        mEditText = (EditText) findViewById(R.id.edit_nfc);
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                , 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter tag = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        } catch (IntentFilter.MalformedMimeTypeException e) {
//            throw new RuntimeException("fail", e);
            Logger.e(e.toString());
        }
        intentFiltersArray = new IntentFilter[]{ndef, tech, tag};
        techListsArray = new String[][]{
                new String[]{IsoDep.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcF.class.getName()},
                new String[]{NfcV.class.getName()},
                new String[]{Ndef.class.getName()},
                new String[]{NdefFormatable.class.getName()},
                new String[]{MifareClassic.class.getName()},
                new String[]{MifareUltralight.class.getName()}
        };
    }

    public void onPause() {
        super.onPause();
        if (null != mAdapter) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    public void onResume() {
        super.onResume();
        if (null != mAdapter) {
            mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            resolveIntent(intent);
        }
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg;
            if (rawMsgs != null) {
                if (rawMsgs.length > 0) {
                    msg = (NdefMessage) rawMsgs[0];
                    mId = parseIdFromTextRecord(msg.getRecords());
                }
            } else {
                // Unknown tag type
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                if (id.length != 0) {
                    mId = String.valueOf(toDec(id));
                } else {
                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                    mId = dumpTagId(tag);
                }
            }
        }
        Logger.d("id-->" + mId);
        mEditText.setText(mId);
    }

    private String parseIdFromTextRecord(NdefRecord[] records) {
        for (NdefRecord record : records) {
            if (null != record && record.getTnf() == NdefRecord.TNF_WELL_KNOWN
                    && Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    byte[] payload = record.getPayload();
                    /*
                     * payload[0] contains the "Status Byte Encodings" field, per the
                     * NFC Forum "Text Record Type Definition" section 3.2.1.
                     *
                     * bit7 is the Text Encoding Field.
                     *
                     * if (Bit_7 == 0): The text is encoded in UTF-8 if (Bit_7 == 1):
                     * The text is encoded in UTF16
                     *
                     * Bit_6 is reserved for future use and must be set to zero.
                     *
                     * Bits 5 to 0 are the length of the IANA language code.
                     */
                    String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                    int languageCodeLength = payload[0] & 0077;
//                String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
                    return new String(payload, languageCodeLength + 1,
                            payload.length - languageCodeLength - 1, textEncoding);
                } catch (UnsupportedEncodingException e) {
                    // should never happen unless we get a malformed tag.
//                throw new IllegalArgumentException(e);
                    Logger.e(e.toString());
                }
            }
        }
        return null;
    }

    private String dumpTagId(Tag tag) {
        if (null != tag) {
            byte[] id = tag.getId();
            if (id.length != 0) {
                return String.valueOf(toDec(id));
            }
        }
        return null;
    }

    private long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }
}
