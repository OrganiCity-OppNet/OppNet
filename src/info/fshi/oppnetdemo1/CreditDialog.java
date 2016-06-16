package info.fshi.oppnetdemo1;

import info.fshi.oppnetdemo1.data.QueueManager;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class CreditDialog extends Dialog {

	private Context mContext;
	
	public CreditDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_credit);
		mContext = context;
		
		TextView creditText = (TextView) findViewById(R.id.credit_dialog_text);
		
		creditText.setText("You have earned " + String.valueOf(QueueManager.getInstance(mContext).packetsReceived + QueueManager.getInstance(mContext).packetsSent) + " credits");
	}

}
