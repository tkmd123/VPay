import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-rule-rate.reducer';
import { IWalletRuleRate } from 'app/shared/model/wallet-rule-rate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletRuleRateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletRuleRateDetail extends React.Component<IWalletRuleRateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletRuleRateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletRuleRate [<b>{walletRuleRateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletRuleRateCode">Wallet Rule Rate Code</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateCode}</dd>
            <dt>
              <span id="walletRuleRateName">Wallet Rule Rate Name</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateName}</dd>
            <dt>
              <span id="walletRuleRateDesc">Wallet Rule Rate Desc</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateDesc}</dd>
            <dt>
              <span id="walletRuleRateFromValue">Wallet Rule Rate From Value</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateFromValue}</dd>
            <dt>
              <span id="walletRuleRateToValue">Wallet Rule Rate To Value</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateToValue}</dd>
            <dt>
              <span id="walletRuleRateDiscount">Wallet Rule Rate Discount</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateDiscount}</dd>
            <dt>
              <span id="walletRuleRateUDF1">Wallet Rule Rate UDF 1</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateUDF1}</dd>
            <dt>
              <span id="walletRuleRateUDF2">Wallet Rule Rate UDF 2</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateUDF2}</dd>
            <dt>
              <span id="walletRuleRateUDF3">Wallet Rule Rate UDF 3</span>
            </dt>
            <dd>{walletRuleRateEntity.walletRuleRateUDF3}</dd>
            <dt>Wallet Rule</dt>
            <dd>{walletRuleRateEntity.walletRule ? walletRuleRateEntity.walletRule.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-rule-rate" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-rule-rate/${walletRuleRateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletRuleRate }: IRootState) => ({
  walletRuleRateEntity: walletRuleRate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletRuleRateDetail);
