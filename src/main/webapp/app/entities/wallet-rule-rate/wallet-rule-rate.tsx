import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './wallet-rule-rate.reducer';
import { IWalletRuleRate } from 'app/shared/model/wallet-rule-rate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletRuleRateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IWalletRuleRateState {
  search: string;
}

export class WalletRuleRate extends React.Component<IWalletRuleRateProps, IWalletRuleRateState> {
  state: IWalletRuleRateState = {
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
    const { walletRuleRateList, match } = this.props;
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
                <th>ID</th>
                <th>Wallet Rule Rate Code</th>
                <th>Wallet Rule Rate Name</th>
                <th>Wallet Rule Rate Desc</th>
                <th>Wallet Rule Rate From Value</th>
                <th>Wallet Rule Rate To Value</th>
                <th>Wallet Rule Rate Discount</th>
                <th>Wallet Rule</th>
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
      </div>
    );
  }
}

const mapStateToProps = ({ walletRuleRate }: IRootState) => ({
  walletRuleRateList: walletRuleRate.entities
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
