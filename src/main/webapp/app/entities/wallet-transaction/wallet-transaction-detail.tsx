import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-transaction.reducer';
import { IWalletTransaction } from 'app/shared/model/wallet-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletTransactionDetail extends React.Component<IWalletTransactionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletTransactionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletTransaction [<b>{walletTransactionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletTransAmount">Wallet Trans Amount</span>
            </dt>
            <dd>{walletTransactionEntity.walletTransAmount}</dd>
            <dt>
              <span id="walletTransDate">Wallet Trans Date</span>
            </dt>
            <dd>
              <TextFormat value={walletTransactionEntity.walletTransDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="walletTransRef">Wallet Trans Ref</span>
            </dt>
            <dd>{walletTransactionEntity.walletTransRef}</dd>
            <dt>
              <span id="walletTransUsername">Wallet Trans Username</span>
            </dt>
            <dd>{walletTransactionEntity.walletTransUsername}</dd>
            <dt>
              <span id="walletTransUDF1">Wallet Trans UDF 1</span>
            </dt>
            <dd>{walletTransactionEntity.walletTransUDF1}</dd>
            <dt>
              <span id="walletTransUDF2">Wallet Trans UDF 2</span>
            </dt>
            <dd>{walletTransactionEntity.walletTransUDF2}</dd>
            <dt>
              <span id="walletTransUDF3">Wallet Trans UDF 3</span>
            </dt>
            <dd>{walletTransactionEntity.walletTransUDF3}</dd>
            <dt>Wallet Transaction Type</dt>
            <dd>{walletTransactionEntity.walletTransactionType ? walletTransactionEntity.walletTransactionType.id : ''}</dd>
            <dt>Wallet</dt>
            <dd>{walletTransactionEntity.wallet ? walletTransactionEntity.wallet.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-transaction" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-transaction/${walletTransactionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletTransaction }: IRootState) => ({
  walletTransactionEntity: walletTransaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTransactionDetail);
