package dam.pmdm.spyrothedragon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.InteractiveGuideBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private InteractiveGuideBinding interactiveGuideBinding;
    NavController navController = null;

    private boolean guiacompleta = false;
    private SharedPreferences prefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        interactiveGuideBinding = binding.includeGuideLayout;
        setContentView(binding.getRoot());

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_characters ||
                    destination.getId() == R.id.navigation_worlds ||
                    destination.getId() == R.id.navigation_collectibles) {
                // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            else {
                // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        prefe = getSharedPreferences("SpyroPrefs", MODE_PRIVATE);
        guiacompleta = prefe.getBoolean("guideCompleted", false);

        if (!guiacompleta) {
            initializeGuide();
        } else {
            interactiveGuideBinding.guideLayout.setVisibility(View.GONE);
        }

    }

    private void initializeGuide() {

        interactiveGuideBinding.guideLayout.setVisibility(View.VISIBLE);


        interactiveGuideBinding.btnCloseGuide.setOnClickListener(this::iniguia);

    }

    private void iniguia(View view) {

        //Usar MediaPlayer para poner sonido al botón
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.pulse);
        mediaPlayer.start();

        // Establecer el listener para cuando la reproducción termine
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Liberar el MediaPlayer cuando la reproducción termine
                mp.release();
            }
        });

        // Ocultar la pantalla de presentación de la guía
        interactiveGuideBinding.milenear.setVisibility(View.GONE);

        // Muestra el botón para saltar guía
        interactiveGuideBinding.btnSkipGuide.setVisibility(View.VISIBLE);
        // Activación de escucha del botón para salir
        interactiveGuideBinding.btnSkipGuide.setOnClickListener(this::salguia);

        navController.navigate(R.id.navigation_characters);
        // Mostramos los elementos animados de la guía
        interactiveGuideBinding.despulso.setVisibility(View.VISIBLE);
        interactiveGuideBinding.pulsoImage.setVisibility(View.VISIBLE);
        interactiveGuideBinding.despulso.setText("Aquí podrás explorar a todos los personajes del juego de Spyro");

        // Hacer la barra de navegación transparente
        binding.navView.setBackgroundColor(Color.TRANSPARENT);

        // Primera animación
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(interactiveGuideBinding.pulsoImage, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(interactiveGuideBinding.pulsoImage, "scaleY", 1f, 0.5f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(interactiveGuideBinding.despulso, "alpha", 0f, 1f);

        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY).before(fadeIn);
        animatorSet.setDuration(1000);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Navegar a la segunda pantalla después de la primera animación
                navController.navigate(R.id.navigation_worlds);

                interactiveGuideBinding.pulsoImage.setVisibility(View.GONE);
                interactiveGuideBinding.pulsoImagedos.setVisibility(View.VISIBLE);
                interactiveGuideBinding.despulso.setText("Aquí podrás explorar a todos los mundos del juego de Spyro");

                ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(interactiveGuideBinding.pulsoImagedos, "scaleX", 1f, 0.5f);
                ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(interactiveGuideBinding.pulsoImagedos, "scaleY", 1f, 0.5f);
                ObjectAnimator fadeIn2 = ObjectAnimator.ofFloat(interactiveGuideBinding.despulso, "alpha", 0f, 1f);

                scaleX2.setRepeatCount(3);
                scaleY2.setRepeatCount(3);

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.play(scaleX2).with(scaleY2).before(fadeIn2);
                animatorSet2.setDuration(1000);

                animatorSet2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Navegar a la tercera pantalla después de la segunda animación
                        navController.navigate(R.id.navigation_collectibles);

                        interactiveGuideBinding.pulsoImagedos.setVisibility(View.GONE);
                        interactiveGuideBinding.pulsoImagetres.setVisibility(View.VISIBLE);
                        interactiveGuideBinding.despulso.setText("Aquí podrás explorar las colecciones del juego de Spyro");

                        ObjectAnimator scaleX3 = ObjectAnimator.ofFloat(interactiveGuideBinding.pulsoImagetres, "scaleX", 1f, 0.5f);
                        ObjectAnimator scaleY3 = ObjectAnimator.ofFloat(interactiveGuideBinding.pulsoImagetres, "scaleY", 1f, 0.5f);
                        ObjectAnimator fadeIn3 = ObjectAnimator.ofFloat(interactiveGuideBinding.despulso, "alpha", 0f, 1f);

                        scaleX3.setRepeatCount(3);
                        scaleY3.setRepeatCount(3);

                        AnimatorSet animatorSet3 = new AnimatorSet();
                        animatorSet3.play(scaleX3).with(scaleY3).before(fadeIn3);
                        animatorSet3.setDuration(1000);

                        animatorSet3.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                // Mostrar y animar la "i" de información al final de todo
                                interactiveGuideBinding.pulsoinfo.setVisibility(View.VISIBLE);
                                interactiveGuideBinding.despulso.setText("BOTÓN DE INFORMACIÓN");
                                // Aplicar la animación XML al botón de información
                                Animation pulse = AnimationUtils.loadAnimation(interactiveGuideBinding.getRoot().getContext(), R.anim.pulse);
                                interactiveGuideBinding.pulsoinfo.startAnimation(pulse);

                                // Esperar dos segundos antes de navegar al fragmento de resumen
                                interactiveGuideBinding.pulsoinfo.postDelayed(() -> {
                                    // Ocultar toda la guía
                                    interactiveGuideBinding.guideLayout.setVisibility(View.GONE);
                                    // Escribir para no volver a mostrar la guía
                                    prefe.edit().putBoolean("guideCompleted", true).apply();
                                    // Ir a la pantalla de despedida
                                    navController.navigate(R.id.finalGuideFragment);
                                }, 2000);
                            }
                        });

                        animatorSet3.start();
                    }
                });

                animatorSet2.start();
            }
        });

        animatorSet.start();
    }

    private void salguia(View view) {

        //Usar MediaPlayer para poner sonido al botón
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.pulse);
        mediaPlayer.start();

        // Establecer el listener para cuando la reproducción termine
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Liberar el MediaPlayer cuando la reproducción termine
                mp.release();
            }
        });


        // Ocultar toda la guía
        interactiveGuideBinding.guideLayout.setVisibility(View.GONE);

        // Escribir para no volver a mostrar la guía
        prefe.edit().putBoolean("guideCompleted", true).apply();
    }


    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else
        if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }



}