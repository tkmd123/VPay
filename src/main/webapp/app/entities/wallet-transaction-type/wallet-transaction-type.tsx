import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudSearchAction, ICrudGetAllAction, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './wallet-transaction-type.reducer';
import { IWalletTransactionType } from 'app/shared/model/wallet-transaction-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IWalletTransactionTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletTransactionTypeState extends IPaginationBaseState {
  search: string;
}

export class WalletTransactionType extends React.Component<IWalletTransactionTypeProps, IWalletTransactionTypeState> {
  state: IWalletTransactionTypeState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
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

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { walletTransactionTypeList, match } = this.props;
    return (
      <div>
        <h2 id="wallet-transaction-type-heading">
          Wallet Transaction Types
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Wallet Transaction Type
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
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeCode')}>
                    Wallet Trans Type Code <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeName')}>
                    Wallet Trans Type Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeDesc')}>
                    Wallet Trans Type Desc <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeFlag')}>
                    Wallet Trans Type Flag <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeUDF1')}>
                    Wallet Trans Type UDF 1 <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeUDF2')}>
                    Wallet Trans Type UDF 2 <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('walletTransTypeUDF3')}>
                    Wallet Trans Type UDF 3 <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {walletTransactionTypeList.map((walletTransactionType, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${walletTransactionType.id}`} color="link" size="sm">
                        {walletTransactionType.id}
                      </Button>
                    </td>
                    <td>{walletTransactionType.walletTransTypeCode}</td>
                    <td>{walletTransactionType.walletTransTypeName}</td>
                    <td>{walletTransactionType.walletTransTypeDesc}</td>
                    <td>{walletTransactionType.walletTransTypeFlag}</td>
                    <td>{walletTransactionType.walletTransTypeUDF1}</td>
                    <td>{walletTransactionType.walletTransTypeUDF2}</td>
                    <td>{walletTransactionType.walletTransTypeUDF3}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${walletTransactionType.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${walletTransactionType.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${walletTransactionType.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ walletTransactionType }: IRootState) => ({
  walletTransactionTypeList: walletTransactionType.entities,
  totalItems: walletTransactionType.totalItems,
  links: walletTransactionType.links,
  entity: walletTransactionType.entity,
  updateSuccess: walletTransactionType.updateSuccess
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTransactionType);
