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
import { getSearchEntities, getEntities } from './partner-transaction.reducer';
import { IPartnerTransaction } from 'app/shared/model/partner-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IPartnerTransactionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IPartnerTransactionState extends IPaginationBaseState {
  search: string;
}

export class PartnerTransaction extends React.Component<IPartnerTransactionProps, IPartnerTransactionState> {
  state: IPartnerTransactionState = {
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
    const { partnerTransactionList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="partner-transaction-heading">
          Partner Transactions
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Partner Transaction
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
                <th className="hand" onClick={this.sort('partnerTransAmount')}>
                  Partner Trans Amount <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partnerTransDate')}>
                  Partner Trans Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partnerTransRef')}>
                  Partner Trans Ref <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partnerTransUsername')}>
                  Partner Trans Username <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partnerTransUDF1')}>
                  Partner Trans UDF 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partnerTransUDF2')}>
                  Partner Trans UDF 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('partnerTransUDF3')}>
                  Partner Trans UDF 3 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Product Type <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Partner <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wallet Transaction <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {partnerTransactionList.map((partnerTransaction, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${partnerTransaction.id}`} color="link" size="sm">
                      {partnerTransaction.id}
                    </Button>
                  </td>
                  <td>{partnerTransaction.partnerTransAmount}</td>
                  <td>
                    <TextFormat type="date" value={partnerTransaction.partnerTransDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{partnerTransaction.partnerTransRef}</td>
                  <td>{partnerTransaction.partnerTransUsername}</td>
                  <td>{partnerTransaction.partnerTransUDF1}</td>
                  <td>{partnerTransaction.partnerTransUDF2}</td>
                  <td>{partnerTransaction.partnerTransUDF3}</td>
                  <td>
                    {partnerTransaction.productType ? (
                      <Link to={`product-type/${partnerTransaction.productType.id}`}>{partnerTransaction.productType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {partnerTransaction.partner ? (
                      <Link to={`partner/${partnerTransaction.partner.id}`}>{partnerTransaction.partner.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {partnerTransaction.walletTransaction ? (
                      <Link to={`wallet-transaction/${partnerTransaction.walletTransaction.id}`}>
                        {partnerTransaction.walletTransaction.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${partnerTransaction.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${partnerTransaction.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${partnerTransaction.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ partnerTransaction }: IRootState) => ({
  partnerTransactionList: partnerTransaction.entities,
  totalItems: partnerTransaction.totalItems
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
)(PartnerTransaction);
