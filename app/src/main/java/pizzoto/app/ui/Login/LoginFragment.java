package pizzoto.app.ui.Login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pizzoto.app.R;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Button buttoninicio = view.findViewById(R.id.ingresoapp);
        Button buttonRegistro = view.findViewById(R.id.registro);
        EditText editTextCorreo = view.findViewById(R.id.correo);
        EditText editTextContrasena = view.findViewById(R.id.contrasena);

        loginViewModel.getConfirmacion().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean confirmacion) {
                if (confirmacion == true){
                    Toast.makeText(getContext(), "USUARIO CONFIMADO", Toast.LENGTH_SHORT).show();
                    NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainActivity3();
                    Navigation.findNavController(getView()).navigate(action);
                }else{
                    Toast.makeText(getContext(), "USUARIO NO ENCONTRADO, POR FAVOR REGISTRESE", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttoninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginViewModel.pasarCrendenciales(String.valueOf(editTextCorreo.getText()),String.valueOf(editTextContrasena.getText())/*,getContext()*/);

            }
        });
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegistroFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
        return view;
    }




}