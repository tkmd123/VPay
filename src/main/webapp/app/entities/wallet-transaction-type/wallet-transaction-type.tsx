import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './wallet-transaction-type.reducer';
import { IWalletTransactionType } from 'app/shared/model/wallet-transaction-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTransactionTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletTransactionTypeState {
  search: string;
}

export class WalletTransactionType extends React.Component<IWalletTransactionTypeProps, IWalletTransactionTypeState> {
  state: IWalletTransactionTypeState = {
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
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Wallet Trans Type Code</th>
                <th>Wallet Trans Type Name</th>
                <th>Wallet Trans Type Desc</th>
                <th>Wallet Trans Type Flag</th>
                <th>Wallet Trans Type UDF 1</th>
                <th>Wallet Trans Type UDF 2</th>
                <th>Wallet Trans Type UDF 3</th>
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
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ walletTransactionType }: IRootState) => ({
  walletTransactionTypeList: walletTransactionType.entities
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
)(WalletTransactionType);
