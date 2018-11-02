import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import {
  ICrudSearchAction,
  ICrudGetAllAction,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './wallet.reducer';
import { IWallet } from 'app/shared/model/wallet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IWalletProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletState extends IPaginationBaseState {
  search: string;
}

export class Wallet extends React.Component<IWalletProps, IWalletState> {
  state: IWalletState = {
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
    const { walletList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="wallet-heading">
          Wallets
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Wallet
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
                <th className="hand" onClick={this.sort('walletNumber')}>
                  Wallet Number <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletIsActive')}>
                  Wallet Is Active <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletDesc')}>
                  Wallet Desc <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletUDF1')}>
                  Wallet UDF 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletUDF2')}>
                  Wallet UDF 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletUDF3')}>
                  Wallet UDF 3 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Product Type <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Partner <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Pay Partner <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {walletList.map((wallet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${wallet.id}`} color="link" size="sm">
                      {wallet.id}
                    </Button>
                  </td>
                  <td>{wallet.walletNumber}</td>
                  <td>{wallet.walletIsActive ? 'true' : 'false'}</td>
                  <td>{wallet.walletDesc}</td>
                  <td>{wallet.walletUDF1}</td>
                  <td>{wallet.walletUDF2}</td>
                  <td>{wallet.walletUDF3}</td>
                  <td>{wallet.productType ? <Link to={`product-type/${wallet.productType.id}`}>{wallet.productType.id}</Link> : ''}</td>
                  <td>{wallet.partner ? <Link to={`partner/${wallet.partner.id}`}>{wallet.partner.id}</Link> : ''}</td>
                  <td>{wallet.payPartner ? <Link to={`pay-partner/${wallet.payPartner.id}`}>{wallet.payPartner.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${wallet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${wallet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${wallet.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ wallet }: IRootState) => ({
  walletList: wallet.entities,
  totalItems: wallet.totalItems
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
)(Wallet);
