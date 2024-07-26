
<?php $__env->startSection('content'); ?>
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                                    

                                    <div class="page-title-right">
                                       
                                    </div>

                                </div>
                            </div>
                        </div>
                       
        
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">CHALLENGES</h4>
                                       
                                        
                                        <div class="table-responsive">
                                           
                                            <table class="table table-hover mb-0">
        
                                                <thead>
                                                    <tr>
                                                        <th>Challenge No</th>
                                                        <th>AttemptDuration</th>
                                                        <th>No of Questions </th>
                                                        <th>OverallMark</th>
                                                        <th>Opendate</th>
                                                        <th>Closedate</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <?php $__currentLoopData = $viewchallenge; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $Challenge): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                                                    <tr>
                                                        <td><?php echo e($challenge->challengeNo); ?></td>
                                                        <td><?php echo e($challenge->attemptDuration); ?></td>
                                                        <td><?php echo e($challenge->noOfQuestions); ?></td>
                                                        <td><?php echo e($challenge->overallMark); ?></td>
                                                        <td><?php echo e($challenge->openDate); ?></td>
                                                        <td><?php echo e($challenge->closeDate); ?></td>
                                                    </tr>
                                                    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
                                                   
                                                </tbody>
                                            </table>
                                        </div>
        
                                    </div>
                                </div>
                            </div>
                            
                            
                        </div>
                        <!-- end row -->
        
                        <div class="row">
                            
                            
                            <div class="col-lg-6">
                                
                            </div>
                        </div>
                        <!-- end row -->
                        
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    
                                </div>
                            </div>
                        </div>
                        <!-- end row -->
                        
                  
                
               
                
         

<?php $__env->stopSection(); ?>
<?php echo $__env->make('layouts/dash', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\xampp\htdocs\Laravel\Math\resources\views/viewchallenge.blade.php ENDPATH**/ ?>