import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import {
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './wallet-transaction.reducer';
import { IWalletTransaction } from 'app/shared/model/wallet-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IWalletTransactionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletTransactionState extends IPaginationBaseState {
  search: string;
}

export class WalletTransaction extends React.Component<IWalletTransactionProps, IWalletTransactionState> {
  state: IWalletTransactionState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
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

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { walletTransactionList, match, totalItems } = this.props;
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
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransAmount')}>
                  Wallet Trans Amount <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransDate')}>
                  Wallet Trans Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransRef')}>
                  Wallet Trans Ref <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransUsername')}>
                  Wallet Trans Username <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransUDF1')}>
                  Wallet Trans UDF 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransUDF2')}>
                  Wallet Trans UDF 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletTransUDF3')}>
                  Wallet Trans UDF 3 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wallet Transaction Type <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wallet <FontAwesomeIcon icon="sort" />
                </th>
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
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ walletTransaction }: IRootState) => ({
  walletTransactionList: walletTransaction.entities,
  totalItems: walletTransaction.totalItems
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
