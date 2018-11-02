import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './wallet-transaction.reducer';
import { IWalletTransaction } from 'app/shared/model/wallet-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTransactionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletTransactionState {
  search: string;
}

export class WalletTransaction extends React.Component<IWalletTransactionProps, IWalletTransactionState> {
  state: IWalletTransactionState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.props.getEntities();
    this.setState({
      search: ''
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { walletTransactionList, match } = this.props;
    return (
      <div>
        <h2 id="wallet-transaction-heading">
          Wallet Transactions
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Wallet Transaction
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput type="text" name="search" value={this.state.search} onChange={this.handleSearch} placeholder="Search" />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Wallet Trans Amount</th>
                <th>Wallet Trans Date</th>
                <th>Wallet Trans Ref</th>
                <th>Wallet Trans Username</th>
                <th>Wallet Trans UDF 1</th>
                <th>Wallet Trans UDF 2</th>
                <th>Wallet Trans UDF 3</th>
                <th>Wallet Transaction Type</th>
                <th>Wallet</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {walletTransactionList.map((walletTransaction, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${walletTransaction.id}`} color="link" size="sm">
                      {walletTransaction.id}
                    </Button>
                  </td>
                  <td>{walletTransaction.walletTransAmount}</td>
                  <td>
                    <TextFormat type="date" value={walletTransaction.walletTransDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{walletTransaction.walletTransRef}</td>
                  <td>{walletTransaction.walletTransUsername}</td>
                  <td>{walletTransaction.walletTransUDF1}</td>
                  <td>{walletTransaction.walletTransUDF2}</td>
                  <td>{walletTransaction.walletTransUDF3}</td>
                  <td>
                    {walletTransaction.walletTransactionType ? (
                      <Link to={`wallet-transaction-type/${walletTransaction.walletTransactionType.id}`}>
                        {walletTransaction.walletTransactionType.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {walletTransaction.wallet ? (
                      <Link to={`wallet/${walletTransaction.wallet.id}`}>{walletTransaction.wallet.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${walletTransaction.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${walletTransaction.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${walletTransaction.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ walletTransaction }: IRootState) => ({
  walletTransactionList: walletTransaction.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTransaction);
