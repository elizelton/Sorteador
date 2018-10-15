package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.Config.i18n;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Pessoa;
import model.Premio;
import org.controlsfx.control.PopOver;
import utility.XPopOver;
import static config.DAO.pessoaRepository;
import static config.DAO.premioRepository;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import org.springframework.data.domain.Sort;

public class PrincipalController implements Initializable
{

    @FXML
    private MaterialDesignIconView btnIncluirPessoa;
    @FXML
    private MaterialDesignIconView btnAlterarPessoa;
    @FXML
    private MaterialDesignIconView btnExcluirPessoa;
    @FXML
    private MaterialDesignIconView btnIncluirPremio;
    @FXML
    private MaterialDesignIconView btnAlterarPremio;
    @FXML
    private MaterialDesignIconView btnExcluirPremio;
    @FXML
    private ImageView imgVwPremio;
    @FXML
    TableView tblViewPessoa;
    @FXML
    TableView tblViewPremio;
    Pessoa pessoa;
    Premio premio;
    char acao;

    @FXML
    private void btnSortearClick()
    {
        XPopOver popOver = new XPopOver("/FXML/Sorteio.fxml", i18n.getString("lbl.sorteio.titulo"), null);

    }

    @FXML
    private void acIncluirPessoa()
    {
        pessoa = new Pessoa();
        acao = INCLUIR;
        showCRUD("Pessoa");
    }

    @FXML
    private void acAlterarPessoa()
    {

        pessoa = (Pessoa) tblViewPessoa.getSelectionModel().getSelectedItem();
        acao = ALTERAR;
        showCRUD("Pessoa");
    }

    @FXML
    private void acExcluirPessoa()
    {
        acao = EXCLUIR;
        pessoa = (Pessoa) tblViewPessoa.getSelectionModel().getSelectedItem();
        showCRUD("Pessoa");

    }

    @FXML
    private void acIncluirPremio()
    {
        premio = new Premio();
        acao = INCLUIR;
        showCRUD("Premio");
    }

    @FXML
    private void acAlterarPremio()
    {

        premio = (Premio) tblViewPremio.getSelectionModel().getSelectedItem();
        acao = ALTERAR;
        showCRUD("Premio");
    }

    @FXML
    private void acExcluirPremio()
    {
        premio = (Premio) tblViewPremio.getSelectionModel().getSelectedItem();
        acao = EXCLUIR;
        showCRUD("Premio");

    }

    private void showCRUD(String tipo)
    {
        String cena = "/fxml/CRUD" + tipo + ".fxml";
        XPopOver popOver = null;
        if (tipo.equals("Pessoa"))
        {
            switch (acao)
            {
                case INCLUIR:
                    popOver = new XPopOver(cena, i18n.getString("lbl.incluir." + tipo), btnIncluirPessoa, PopOver.ArrowLocation.TOP_LEFT);
                    break;
                case ALTERAR:
                    popOver = new XPopOver(cena, i18n.getString("lbl.alterar." + tipo), btnAlterarPessoa, PopOver.ArrowLocation.TOP_LEFT);
                    break;
                case EXCLUIR:
                    popOver = new XPopOver(cena, i18n.getString("lbl.excluir." + tipo), btnExcluirPessoa, PopOver.ArrowLocation.TOP_LEFT);
                    break;
            }
            CRUDPessoaController controllerFilho = popOver.getLoader().getController();
            controllerFilho.setCadastroController(this);
        } else
        {
            switch (acao)
            {
                case INCLUIR:
                    popOver = new XPopOver(cena, i18n.getString("lbl.incluir." + tipo), btnIncluirPremio, PopOver.ArrowLocation.TOP_LEFT);
                    break;
                case ALTERAR:
                    popOver = new XPopOver(cena, i18n.getString("lbl.alterar." + tipo), btnAlterarPremio, PopOver.ArrowLocation.TOP_LEFT);
                    break;
                case EXCLUIR:
                    popOver = new XPopOver(cena, i18n.getString("lbl.excluir." + tipo), btnExcluirPremio, PopOver.ArrowLocation.TOP_LEFT);
                    break;
            }
            CRUDPremioController controllerFilho = popOver.getLoader().getController();
            controllerFilho.setCadastroController(this);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        imgVwPremio.setImage(new Image("/img/premio.png"));
        tblViewPremio.setItems(FXCollections.observableList(premioRepository.findAll(new Sort(new Sort.Order("nome")))));
        tblViewPessoa.setItems(FXCollections.observableList(pessoaRepository.findAll(new Sort(new Sort.Order("nome")))));
        btnAlterarPessoa.visibleProperty().bind(Bindings.isEmpty((tblViewPessoa.getSelectionModel().getSelectedItems())).not());
        btnExcluirPessoa.visibleProperty().bind(btnAlterarPessoa.visibleProperty());
        btnAlterarPremio.visibleProperty().bind(Bindings.isEmpty((tblViewPremio.getSelectionModel().getSelectedItems())).not());
        btnExcluirPremio.visibleProperty().bind(btnAlterarPremio.visibleProperty());
    }
}
