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

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Premio p = getPremioRand();
        txtFldPremio.setText(p.getNome());
        txtFldDescricao.setText(p.getDescricao());
    }

    private Premio getPremioRand()
    {
        List<Premio> lstTemp = new ArrayList<Premio>();
        lstTemp = premioRepository.findAll();
        return lstTemp.get(new Random().nextInt(lstTemp.size()));
    }
}
