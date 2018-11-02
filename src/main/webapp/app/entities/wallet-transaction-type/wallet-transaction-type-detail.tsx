import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-transaction-type.reducer';
import { IWalletTransactionType } from 'app/shared/model/wallet-transaction-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTransactionTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletTransactionTypeDetail extends React.Component<IWalletTransactionTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletTransactionTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletTransactionType [<b>{walletTransactionTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletTransTypeCode">Wallet Trans Type Code</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeCode}</dd>
            <dt>
              <span id="walletTransTypeName">Wallet Trans Type Name</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeName}</dd>
            <dt>
              <span id="walletTransTypeDesc">Wallet Trans Type Desc</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeDesc}</dd>
            <dt>
              <span id="walletTransTypeFlag">Wallet Trans Type Flag</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeFlag}</dd>
            <dt>
              <span id="walletTransTypeUDF1">Wallet Trans Type UDF 1</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeUDF1}</dd>
            <dt>
              <span id="walletTransTypeUDF2">Wallet Trans Type UDF 2</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeUDF2}</dd>
            <dt>
              <span id="walletTransTypeUDF3">Wallet Trans Type UDF 3</span>
            </dt>
            <dd>{walletTransactionTypeEntity.walletTransTypeUDF3}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-transaction-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-transaction-type/${walletTransactionTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletTransactionType }: IRootState) => ({
  walletTransactionTypeEntity: walletTransactionType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTransactionTypeDetail);
