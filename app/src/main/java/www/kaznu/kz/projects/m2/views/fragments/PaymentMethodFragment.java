package www.kaznu.kz.projects.m2.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.CurrencyAdapter;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Properties;

public class PaymentMethodFragment extends Fragment implements Constants {
    LinearLayout btnAddPaymentCard;
    Spinner currency;
    CurrencyAdapter adapter;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    CurrentUser user;
    Properties properties;

    public PaymentMethodFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_payment_method, container, false);
        btnAddPaymentCard = fv.findViewById(R.id.add_payment_card);

        currency = fv.findViewById(R.id.sp_currency);

        user = new CurrentUser(requireContext());
        properties = new Properties(requireContext());

        adapter = new CurrencyAdapter(requireContext(), properties.getCurrencies());

        currency.setAdapter(adapter);

        btnAddPaymentCard.setOnClickListener(v -> {
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();

            PaymentCardFragment paymentCardFragment = new PaymentCardFragment();
            ft.replace(R.id.payment_methods, paymentCardFragment, "PaymentCardFragment");
            ft.commit();
        });

        return fv;
    }
}