package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Premio;
import static config.DAO.premioRepository;
import static config.DAO.pessoaRepository;
import java.time.LocalDateTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;
import model.Pessoa;

/**
 * FXML Controller class
 *
 * @author Elizelton
 */
public class SorteioController implements Initializable
{

    @FXML
    private TableView tbViewSorteio;
    @FXML
    private TextField txtFldPremio;
    @FXML
    private TextField txtFldDescricao;
    private Timeline timeline = new Timeline();

    private Premio premioSort;
    private Pessoa pessoaSort;
    private List<Pessoa> pessoasSortFake = new ArrayList<Pessoa>();
    private List<Pessoa> pessoasSort = new ArrayList<Pessoa>();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        sortear();

        txtFldPremio.setText(premioSort.getNome());
        txtFldDescricao.setText(premioSort.getDescricao());
        pessoasSort.add(pessoaSort);
        pessoasSort.addAll(pessoasSortFake);
        pessoaRepository.delete(pessoaSort);
        premioRepository.delete(premioSort);
        tbViewSorteio.setItems(FXCollections.observableList(pessoasSort));

        fazAMagica();

    }

    private void sortear()
    {

        List<Premio> premiosTemp = premioRepository.findAll();
        List<Pessoa> pessoasTemp = pessoaRepository.findAll();
        Pessoa pessoaSortTemp;
        premioSort = premiosTemp.get(new Random().nextInt(premiosTemp.size()));
        pessoaSort = pessoasTemp.get(new Random().nextInt(pessoasTemp.size()));
        pessoasTemp.remove(pessoaSort);
        for (int i = 0; i < 7; i++)
        {
            int j = new Random().nextInt(pessoasTemp.size());
            pessoaSortTemp = pessoasTemp.get(j);
            pessoasSortFake.add(pessoaSortTemp);
            pessoasTemp.remove(pessoaSortTemp);
        }
    }

    public void fazAMagica()
    {

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        EventHandler ev = new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                if (!pessoasSortFake.isEmpty())
                {
                    tbViewSorteio.setItems(FXCollections.observableList(pessoasSort));
                    tbViewSorteio.sort();
                    tbViewSorteio.refresh();
                    pessoasSortFake.remove(0);
                    pessoasSort.clear();
                    pessoasSort.addAll(pessoasSortFake);
                    pessoasSort.add(pessoaSort);
                } else
                {
                    timeline.stop();
                }
            }
        };
        KeyFrame kf = new KeyFrame(Duration.millis(1000), ev);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

}
