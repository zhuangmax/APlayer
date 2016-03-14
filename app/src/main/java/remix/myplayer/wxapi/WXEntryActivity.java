package remix.myplayer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import remix.myplayer.ui.SharePopupWindow;
import remix.myplayer.utils.Constants;
import remix.myplayer.utils.SharedPrefsUtil;


/**
 * Created by taeja on 16-3-3.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    private IWXAPI mWechatApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWechatApi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APIID);
        mWechatApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWechatApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("WX",baseReq.toString());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("WX",baseResp.toString());
        if(SharePopupWindow.mInstance != null ){
            String result = "";
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "发送成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "发送取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送失败";
                    break;
                default:
                    result = "位置错误";
                    break;
            }
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            SharePopupWindow.mInstance.finish();
        }

    }
}