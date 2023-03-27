class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var cameraView: CameraBridgeViewBase
    private lateinit var cameraBridge: BaseLoaderCallback
    private lateinit var imgProc: Mat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraView = findViewById(R.id.camera_view)
        cameraView.visibility = SurfaceView.VISIBLE
        cameraView.setCvCameraViewListener(this)

        cameraBridge = object : BaseLoaderCallback(this) {
            override fun onManagerConnected(status: Int) {
                when (status) {
                    LoaderCallbackInterface.SUCCESS -> cameraView.enableView()
                    else -> super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, cameraBridge)
    }

    override fun onPause() {
        super.onPause()
        cameraView.disableView()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        imgProc = Mat(height, width, CvType.CV_8UC4)
    }

    override fun onCameraViewStopped
