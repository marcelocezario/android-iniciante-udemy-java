package br.com.marcelocezario.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.marcelocezario.festafimdeano.R;
import br.com.marcelocezario.festafimdeano.constant.FimDeAnoConstants;
import br.com.marcelocezario.festafimdeano.data.SecurityPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday = findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = findViewById(R.id.button_confirm);

        this.mViewHolder.buttonConfirm.setOnClickListener(this);

        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLeft()), getString(R.string.dias));
        this.mViewHolder.textDaysLeft.setText(daysLeft);

    }

    private void verifyPresence() {
        // não confirmado, sim, não
        String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);
        if (presence.equals("")) {
            this.mViewHolder.buttonConfirm.setText(getString(R.string.nao_confirmado));
        } else if (presence.equals(FimDeAnoConstants.CONFIRMATION_YES)) {
            this.mViewHolder.buttonConfirm.setText((getString(R.string.sim)));
        } else {
            this.mViewHolder.buttonConfirm.setText((getString(R.string.nao)));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_confirm){

            String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY, presence);
            startActivity(intent);
        }
    }

    private int getDaysLeft() {
        // Data de hoje
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);
        // Dia máximo do ano (de 1 a 365)
        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}
