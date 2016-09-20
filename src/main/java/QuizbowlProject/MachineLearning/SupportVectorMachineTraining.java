package QuizbowlProject.MachineLearning;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import edu.berkeley.compbio.jlibsvm.ImmutableSvmParameter;
import edu.berkeley.compbio.jlibsvm.ImmutableSvmParameterGrid;
import edu.berkeley.compbio.jlibsvm.SVM;
import edu.berkeley.compbio.jlibsvm.binary.BinaryClassificationSVM;
import edu.berkeley.compbio.jlibsvm.binary.C_SVC;
import edu.berkeley.compbio.jlibsvm.kernel.CompositeGaussianRBFKernel;
import edu.berkeley.compbio.jlibsvm.kernel.GammaKernel;
import edu.berkeley.compbio.jlibsvm.kernel.LinearKernel;
import edu.berkeley.compbio.jlibsvm.multi.BatchClusterLabelInverter;
import edu.berkeley.compbio.jlibsvm.multi.MultiClassModel;
import edu.berkeley.compbio.jlibsvm.multi.MultiClassificationSVM;
import edu.berkeley.compbio.jlibsvm.multi.MutableMultiClassProblemImpl;
import edu.berkeley.compbio.jlibsvm.scaler.LinearScalingModelLearner;
import edu.berkeley.compbio.jlibsvm.scaler.NoopScalingModel;
import edu.berkeley.compbio.jlibsvm.util.SparseVector;

public class SupportVectorMachineTraining {
	
	/*
	- instantiate the KernelFunction that you want
	- set up some parameters in a new SvmParameter object
	- instantiate a concrete subclass of SvmProblem (binary, multiclass, or regression), and populate it with training data
	- instantiate a concrete subclass of SVM, choosing a type appropriate for your problem
	- call SVM.train(problem) to yield a SolutionModel, which can be used to make predictions
	 */
	
	public void train() throws SAXException, IOException, TikaException {
		
		 int numExamples = 2;
		
		 ImmutableSvmParameterGrid.Builder builder = ImmutableSvmParameterGrid.builder();
		 ImmutableSvmParameter parameter = builder.build();
		 
		 NoopScalingModel noopScalingModel = new NoopScalingModel();
		 BatchClusterLabelInverter batchClusterLabelInverter = new BatchClusterLabelInverter();
		 
		 LinearKernel s = new LinearKernel();
		 
		 ArrayList<LinearKernel> kernels = new ArrayList<LinearKernel>();
		 LinearKernel kernel = new LinearKernel();
		 kernels.add(kernel);
		 
		 builder.kernelSet = kernels;
		 
		 MutableMultiClassProblemImpl problem = new MutableMultiClassProblemImpl(int.class, batchClusterLabelInverter, numExamples, noopScalingModel);
		 
		 populateProblem(problem);
		 
		 C_SVC cSvm = new C_SVC();
		 MultiClassificationSVM svm = new MultiClassificationSVM(cSvm);
		 MultiClassModel model = svm.train(problem, parameter);
		 
		
	}
	
	public static void populateProblem(MutableMultiClassProblemImpl problem) throws SAXException, IOException, TikaException {
		GetTrainingData getTrainingData = new GetTrainingData();
		double[][] X = getTrainingData.getTossupFeatureMatrix();
		int[] y = getTrainingData.getTrainingDataClassifications();
		int numFeatures = X[0].length;
		
		float[][] Xfloat = new float[X.length][numFeatures];
		
		for (int i = 0; i < X.length; i ++) {
			for (int j = 0; j < numFeatures; j ++) {
				Xfloat[i][j] = (float)X[i][j];
			}
		}
		
		
		for (int i = 0; i < y.length; i ++) {
			SparseVector example = new SparseVector(numFeatures);
			example.values = Xfloat[i];
			problem.addExample(example, y[i]);
		}
	}

}
