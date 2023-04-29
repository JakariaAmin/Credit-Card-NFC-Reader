package com.pro100svitlo.creditCardNfcReader.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.os.Build;

/**
 * Created by pro100svitlo on 31.03.16.
 */
public class CardNfcUtils {

    private final NfcAdapter mNfcAdapter;
    private final PendingIntent mPendingIntent;
    private final Activity mActivity;
    private static final IntentFilter[] INTENT_FILTER = new IntentFilter[] {
            new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)};
    private static final String[][] TECH_LIST = new String[][] { {
            NfcA.class.getName(), IsoDep.class.getName() } };

    public CardNfcUtils(final Activity pActivity) {
        mActivity = pActivity;
        mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mPendingIntent = PendingIntent.getActivity(mActivity, 0,
                    new Intent(mActivity, mActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            mPendingIntent = PendingIntent.getActivity(mActivity, 0,
                    new Intent(mActivity, mActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }
    }

    public void disableDispatch() {
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(mActivity);
        }
    }

    public void enableDispatch() {
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(mActivity, mPendingIntent, INTENT_FILTER, TECH_LIST);
        }
    }
}
