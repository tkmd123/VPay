import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-rule.reducer';
import { IWalletRule } from 'app/shared/model/wallet-rule.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletRuleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletRuleDetail extends React.Component<IWalletRuleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletRuleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletRule [<b>{walletRuleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletRuleCode">Wallet Rule Code</span>
            </dt>
            <dd>{walletRuleEntity.walletRuleCode}</dd>
            <dt>
              <span id="walletRuleName">Wallet Rule Name</span>
            </dt>
            <dd>{walletRuleEntity.walletRuleName}</dd>
            <dt>
              <span id="walletRuleDesc">Wallet Rule Desc</span>
            </dt>
            <dd>{walletRuleEntity.walletRuleDesc}</dd>
            <dt>
              <span id="walletRuleFromDate">Wallet Rule From Date</span>
            </dt>
            <dd>
              <TextFormat value={walletRuleEntity.walletRuleFromDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="walletRuleToDate">Wallet Rule To Date</span>
            </dt>
            <dd>
              <TextFormat value={walletRuleEntity.walletRuleToDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="walletRuleUDF1">Wallet Rule UDF 1</span>
            </dt>
            <dd>{walletRuleEntity.walletRuleUDF1}</dd>
            <dt>
              <span id="walletRuleUDF2">Wallet Rule UDF 2</span>
            </dt>
            <dd>{walletRuleEntity.walletRuleUDF2}</dd>
            <dt>
              <span id="walletRuleUDF3">Wallet Rule UDF 3</span>
            </dt>
            <dd>{walletRuleEntity.walletRuleUDF3}</dd>
            <dt>Product Type</dt>
            <dd>{walletRuleEntity.productType ? walletRuleEntity.productType.id : ''}</dd>
            <dt>Pay Partner</dt>
            <dd>{walletRuleEntity.payPartner ? walletRuleEntity.payPartner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-rule" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-rule/${walletRuleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletRule }: IRootState) => ({
  walletRuleEntity: walletRule.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletRuleDetail);
