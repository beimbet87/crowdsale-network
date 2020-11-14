package www.kaznu.kz.projects.m2.models;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import www.kaznu.kz.projects.m2.R;

public class SearchDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;

    public SearchDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_dialog);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("нажмите на иконку").append("  ");
        builder.setSpan(new ImageSpan(getContext(), R.drawable.ic_edit),
                builder.length() - 1, builder.length(), 0);
        builder.append(", чтобы войти в режим выделения области или выйти из него.");
        builder.append(" Нажмите на иконку  ");
        builder.setSpan(new ImageSpan(getContext(), R.drawable.ic_trash),
                builder.length() - 1, builder.length(), 0);
        builder.append(" чтобы удалить все точки.");
        TextView tvSearchInfo = findViewById(R.id.search_dialog_info);
        tvSearchInfo.setText(builder);

        yes = (Button) findViewById(R.id.btn_ok);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}