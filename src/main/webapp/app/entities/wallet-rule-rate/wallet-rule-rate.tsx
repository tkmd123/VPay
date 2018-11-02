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
import { getSearchEntities, getEntities } from './wallet-rule-rate.reducer';
import { IWalletRuleRate } from 'app/shared/model/wallet-rule-rate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IWalletRuleRateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletRuleRateState extends IPaginationBaseState {
  search: string;
}

export class WalletRuleRate extends React.Component<IWalletRuleRateProps, IWalletRuleRateState> {
  state: IWalletRuleRateState = {
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
    const { walletRuleRateList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="wallet-rule-rate-heading">
          Wallet Rule Rates
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Wallet Rule Rate
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
                <th className="hand" onClick={this.sort('walletRuleRateCode')}>
                  Wallet Rule Rate Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateName')}>
                  Wallet Rule Rate Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateDesc')}>
                  Wallet Rule Rate Desc <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateFromValue')}>
                  Wallet Rule Rate From Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateToValue')}>
                  Wallet Rule Rate To Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateDiscount')}>
                  Wallet Rule Rate Discount <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateUDF1')}>
                  Wallet Rule Rate UDF 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateUDF2')}>
                  Wallet Rule Rate UDF 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('walletRuleRateUDF3')}>
                  Wallet Rule Rate UDF 3 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wallet Rule <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {walletRuleRateList.map((walletRuleRate, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${walletRuleRate.id}`} color="link" size="sm">
                      {walletRuleRate.id}
                    </Button>
                  </td>
                  <td>{walletRuleRate.walletRuleRateCode}</td>
                  <td>{walletRuleRate.walletRuleRateName}</td>
                  <td>{walletRuleRate.walletRuleRateDesc}</td>
                  <td>{walletRuleRate.walletRuleRateFromValue}</td>
                  <td>{walletRuleRate.walletRuleRateToValue}</td>
                  <td>{walletRuleRate.walletRuleRateDiscount}</td>
                  <td>{walletRuleRate.walletRuleRateUDF1}</td>
                  <td>{walletRuleRate.walletRuleRateUDF2}</td>
                  <td>{walletRuleRate.walletRuleRateUDF3}</td>
                  <td>
                    {walletRuleRate.walletRule ? (
                      <Link to={`wallet-rule/${walletRuleRate.walletRule.id}`}>{walletRuleRate.walletRule.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${walletRuleRate.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${walletRuleRate.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${walletRuleRate.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ walletRuleRate }: IRootState) => ({
  walletRuleRateList: walletRuleRate.entities,
  totalItems: walletRuleRate.totalItems
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
)(WalletRuleRate);
