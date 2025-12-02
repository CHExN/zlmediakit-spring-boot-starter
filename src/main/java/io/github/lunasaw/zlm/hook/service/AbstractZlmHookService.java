package io.github.lunasaw.zlm.hook.service;

import io.github.lunasaw.zlm.hook.param.*;

/**
 * ZLM Hook 服务抽象实现
 *
 * @author CHEaN
 */
public abstract class AbstractZlmHookService implements ZlmHookService {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFlowReport(HookParamForOnFlowReport param) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HookResultForOnHttpAccess onHttpAccess(HookParamForOnHttpAccess param) {
        return HookResultForOnHttpAccess.success();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link HookResult#success()}
     */
    @Override
    public HookResult onPlay(HookParamForOnPlay param) {
        return HookResult.success();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link HookResultForOnPublish#success()}
     */
    @Override
    public HookResultForOnPublish onPublish(HookParamForOnPublish param) {
        return HookResultForOnPublish.success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRecordMp4(HookParamForOnRecordMP4 param) {

    }

    /**
     * {@inheritDoc}
     *
     * @return {@link HookResultForOnRtspAuth#success(boolean, String)}
     */
    @Override
    public HookResultForOnRtspAuth onRtspAuth(HookParamForOnRtspAuth param) {
        return HookResultForOnRtspAuth.success(false, "");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link HookResultForOnRtspRealm#noAuthRequired()}
     */
    @Override
    public HookResultForOnRtspRealm onRtspRealm(HookParamForOnRtspRealm param) {
        return HookResultForOnRtspRealm.noAuthRequired();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link HookResult#success()}
     */
    @Override
    public HookResult onShellLogin(HookParamForOnShellLogin param) {
        return HookResult.success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStreamChanged(HookParamForOnStreamChanged param) {

    }

    /**
     * {@inheritDoc}
     *
     * @return {@link HookResultForOnStreamNoneReader#success()}
     */
    @Override
    public HookResultForOnStreamNoneReader onStreamNoneReader(HookParamForOnStreamNoneReader param) {
        return HookResultForOnStreamNoneReader.success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStreamNotFound(HookParamForOnStreamNotFound param) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServerStarted(HookParamForOnServerStarted param) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServerExited(HookParamForOnServerExited param) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onServerKeepLive(HookParamForOnServerKeepalive param) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSendRtpStopped(HookParamForOnSendRtpStopped param) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRtpServerTimeout(HookParamForOnRtpServerTimeout param) {

    }

}
